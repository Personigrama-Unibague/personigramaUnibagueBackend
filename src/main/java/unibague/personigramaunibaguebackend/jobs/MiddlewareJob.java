package unibague.personigramaunibaguebackend.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import unibague.personigramaunibaguebackend.model.DependenciesMDW;
import unibague.personigramaunibaguebackend.model.FunctionariesMDW;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.model.Unidad;
import unibague.personigramaunibaguebackend.repository.IPersonalRepository;
import unibague.personigramaunibaguebackend.repository.IRolesRepository;
import unibague.personigramaunibaguebackend.repository.IUnidadesRepository;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Job encargado de obtener la información del Middleware y realizar procesos de validación contra la BD interna.
 */
@Component
@EnableScheduling
public class MiddlewareJob {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private IUnidadesRepository iUnidadesRepository;

    @Autowired
    private IPersonalRepository iPersonalRepository;

    @Autowired
    private IRolesRepository iRolesRepository;

    private String token = "$2y$10$s/5xSDieUMEvYD/gfNqFAeFzvWXt13jhWuugpJzQ9rZQrbGpBYUxi";
    private final String DEFAULT_PHONE = "6082795225"; // Teléfono fijo


    /**
     * Método para validar y descargar las unidades, los roles y las personas relacionadas a la misma.
     */
    @Scheduled(fixedDelay = 3600000) // Se ejecuta cada 5 minutos después de terminar
    public void updateDependenciesMDW() {

        try {
            String urlUndMDW = "http://integra.unibague.edu.co/functionariesChart/dependencies?api_token=";
            DependenciesMDW[] res = restTemplate.getForObject(urlUndMDW + token, DependenciesMDW[].class);
            List<DependenciesMDW> response = res != null ? List.of(res) : new ArrayList<>();

            List<Unidad> unidadesMDW = new ArrayList<>();
            List<Unidad> unidadesBD = iUnidadesRepository.getAllUnidades();
            List<Unidad> newUnidades = new ArrayList<>();
            List<Unidad> toDelete = new ArrayList<>();

            for (DependenciesMDW dependency : response) {
                Unidad unidad = new Unidad();
                unidad.setId(Optional.ofNullable(dependency.getDep_code()).orElse("SIN_ID"));
                unidad.setNombre(Optional.ofNullable(dependency.getDep_name()).orElse("SIN_NOMBRE"));
                unidad.setParent_id(Optional.ofNullable(dependency.getDep_father()).orElse("SIN_PARENT"));

                unidadesMDW.add(unidad);
            }

            // Identificar unidades a eliminar
            for (Unidad BD : unidadesBD) {
                boolean existeEnMDW = unidadesMDW.stream()
                        .anyMatch(MDW -> BD.getId().equals(MDW.getId()));

                if (!existeEnMDW) {
                    toDelete.add(BD);
                }
            }

            for (Unidad unidadToDelete : toDelete) {
                iPersonalRepository.deleteByUnidad(unidadToDelete.getId());
                iRolesRepository.deleteRolByUnidad(unidadToDelete.getId());
                iUnidadesRepository.deleteUnidadById(unidadToDelete.getId());
            }

            // Identificar unidades a agregar o actualizar
            for (Unidad MDW : unidadesMDW) {
                Optional<Unidad> unidadBDOpt = unidadesBD.stream()
                        .filter(BD -> BD.getId().equals(MDW.getId()))
                        .findFirst();

                if (unidadBDOpt.isPresent()) {
                    Unidad BD = unidadBDOpt.get();

                    if (!Objects.equals(BD.getNombre(), MDW.getNombre()) ||
                            !Objects.equals(BD.getParent_id(), MDW.getParent_id())) {

                        iUnidadesRepository.updateUnidadesMDW(MDW.getId(), MDW.getNombre(), MDW.getParent_id());
                        System.out.println("Unidad " + MDW.getNombre() + " Actualizada");
                    }
                } else {
                    newUnidades.add(MDW);
                }
            }

            if (!newUnidades.isEmpty()) {
                iUnidadesRepository.saveAll(newUnidades);
            }

            System.out.println("---------------------------------------------");
            System.out.println("Fecha: " + LocalDateTime.now());
            System.out.println("Unidades Agregadas: " + newUnidades.size());
            System.out.println("Unidades Eliminadas: " + toDelete.size());
            System.out.println("---------------------------------------------");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Método para validar y descargar las personas y sus unidades desde el Middleware.
     */
    @Scheduled(fixedDelay = 3600000) // Se ejecuta cada 5 minutos después de terminar
    public void updateFunctionariesMDW() {
        try {
            String urlPerMDW = "http://integra.unibague.edu.co/functionariesChart/functionaries?api_token=";
            FunctionariesMDW[] res = restTemplate.getForObject(urlPerMDW + token, FunctionariesMDW[].class);
            List<FunctionariesMDW> response = res != null ? Arrays.asList(res) : new ArrayList<>();

            // Obtener la información actual de la base de datos
            List<Personal> personalBD = iPersonalRepository.getAllPersonal();
            List<Personal> personalMDW = new ArrayList<>();
            List<Personal> newPersonal = new ArrayList<>();
            List<Personal> toDelete = new ArrayList<>();

            // Procesar datos del Middleware
            for (FunctionariesMDW functionary : response) {
                Personal personal = new Personal();

                // Convertir valores para evitar nulls
                personal.setCedula(Optional.ofNullable(functionary.getIdentification()).orElse("SIN_CEDULA"));
                personal.setNombre(Optional.ofNullable(functionary.getName()).orElse("") + " " +
                        Optional.ofNullable(functionary.getLast_name()).orElse(""));
                personal.setCargo(Optional.ofNullable(functionary.getPosition()).orElse("SIN_CARGO"));
                personal.setExtension(parseExtension(functionary.getExtension())); // Validación de número
                personal.setCorreo(Optional.ofNullable(functionary.getEmail()).orElse("SIN_CORREO"));
                personal.setUnidad(Optional.ofNullable(functionary.getDep_code()).orElse("SIN_UNIDAD"));
                personal.setFoto(functionary.getDns_photo() + "/" + functionary.getDir_photo() + functionary.getId_photo());

                // Asignar el teléfono fijo a todos los registros
                personal.setTelefono(DEFAULT_PHONE);

                personal.setId_jerar(0);
                personal.setOriginal("ORIGINAL");

                personalMDW.add(personal);
            }

            // Encontrar personas que deben eliminarse
            for (Personal personaBD : personalBD) {
                boolean encontrado = personalMDW.stream().anyMatch(personaMDW ->
                        personaMDW.getCedula() != null && personaMDW.getCedula().equals(personaBD.getCedula()));

                if (!encontrado) {
                    toDelete.add(personaBD);
                }
            }

            // Eliminar personas que ya no existen en el Middleware
            for (Personal personToDelete : toDelete) {
                iPersonalRepository.deleteByCedula(personToDelete.getCedula());
            }

            // Verificar qué personas deben actualizarse o agregarse
            for (Personal personaMDW : personalMDW) {
                Optional<Personal> personaBDOpt = personalBD.stream()
                        .filter(p -> p.getCedula().equals(personaMDW.getCedula()))
                        .findFirst();

                if (personaBDOpt.isPresent()) {
                    Personal personaBD = personaBDOpt.get();
                    boolean necesitaActualizar = false;

                    // Verificar cambios en cargo, extensión, foto, correo, teléfono y unidad
                    if (!Objects.equals(personaMDW.getCargo(), personaBD.getCargo()) ||
                            !Objects.equals(personaMDW.getExtension(), personaBD.getExtension()) ||
                            !Objects.equals(personaMDW.getFoto(), personaBD.getFoto()) ||
                            !Objects.equals(personaMDW.getCorreo(), personaBD.getCorreo()) ||
                            !Objects.equals(DEFAULT_PHONE, personaBD.getTelefono())) {  // ACTUALIZAR TELÉFONO

                        iPersonalRepository.updateMDWChangingValues(
                                personaMDW.getCargo(),
                                personaMDW.getExtension(),
                                personaMDW.getFoto(),
                                personaMDW.getCorreo(),
                                DEFAULT_PHONE,  // Actualizar con el nuevo número
                                personaBD.getCedula()
                        );

                        System.out.println(" Persona actualizada: " + personaMDW.getNombre());
                        necesitaActualizar = true;
                    }

                    // Si la unidad cambió, actualizarla junto con el teléfono
                    if (!Objects.equals(personaMDW.getUnidad(), personaBD.getUnidad())) {
                        iPersonalRepository.updateOriginalUnidadMDW(personaMDW.getUnidad(), DEFAULT_PHONE, personaBD.getCedula());
                        System.out.println(" Unidad y teléfono actualizados para: " + personaMDW.getNombre());
                        necesitaActualizar = true;
                    }

                    if (!necesitaActualizar) {
                        System.out.println("✔️ No se requieren cambios para: " + personaMDW.getNombre());
                    }

                } else {
                    newPersonal.add(personaMDW);
                }
            }

            // Guardar nuevas personas en la BD
            iPersonalRepository.saveAll(newPersonal);

            System.out.println("---------------------------------------------");
            System.out.println("Fecha: " + LocalDateTime.now());
            System.out.println(" Personas Agregadas: " + newPersonal.size());
            System.out.println(" Personas Eliminadas: " + toDelete.size());
            System.out.println("---------------------------------------------");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Método para convertir extensión a número, manejando valores no numéricos.
     */
    private Integer parseExtension(String extension) {
        try {
            return Integer.parseInt(extension.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
