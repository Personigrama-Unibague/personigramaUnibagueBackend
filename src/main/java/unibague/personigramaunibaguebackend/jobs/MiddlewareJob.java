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
import java.util.ArrayList;
import java.util.List;

/**
 * Job encargado de obtener la informacion del Middleware y realizar procesos de validacion contra la bd interna
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

    /**
     * Metodo para validar y descargar las unidades, los roles y las personas relacionadas a la misma
     */
    @Scheduled(fixedRate = 2000000)
    public void updateDependenciesMDW() {

        try {
            String urlUndMDW = "http://integra.unibague.edu.co/functionariesChart/dependencies?api_token=";
            DependenciesMDW[] res = restTemplate.getForObject(urlUndMDW + token, DependenciesMDW[].class);
            List<DependenciesMDW> response = List.of(res);
            Integer counterRoles = 0;
            Integer counterPersonal = 0;

            //Procesar Data
            List<Unidad> unidadesMDW = new ArrayList<>();
            List<Unidad> unidadesBD = iUnidadesRepository.getAllUnidades();
            List<Unidad> newUnidades = new ArrayList<>();
            List<Unidad> toDelete = new ArrayList<>();

            for (int i = 0; i < response.size(); i++) {
                Unidad unidad = new Unidad();
                unidad.setId(response.get(i).getDep_code());
                unidad.setNombre(response.get(i).getDep_name());
                unidad.setParent_id(response.get(i).getDep_father());

                unidadesMDW.add(unidad);
            }

            //Para borrar unidades y sus roles relacionados
            for (Unidad BD : unidadesBD) {
                boolean encontrado = false;
                for (Unidad MDW : unidadesMDW) {
                    if (BD.getId().equals(MDW.getId())) {
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    toDelete.add(BD);
                }
            }

            for (Unidad unidadToDelete : toDelete) {
                counterPersonal += iPersonalRepository.countPersonalByUnidad(unidadToDelete.getId());
                counterRoles += iRolesRepository.countRolByUnidad(unidadToDelete.getId());
                iPersonalRepository.deleteByUnidad(unidadToDelete.getId());
                iUnidadesRepository.deleteUnidadById(unidadToDelete.getId());
                iRolesRepository.deleteRolByUnidad(unidadToDelete.getId());
            }

            //Para Agregar Unidades
            for (Unidad MDW : unidadesMDW) {
                boolean encontrado = false;
                for (Unidad BD : unidadesBD) {
                    if (BD.getId().equals(MDW.getId())) {
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    newUnidades.add(MDW);
                }
            }
            iUnidadesRepository.saveAll(newUnidades);

            System.out.println("---------------------------------------------");
            System.out.println("Fecha: " + LocalDateTime.now());
            System.out.println("Unidades Agregadas: " + newUnidades.size());
            System.out.println("Unidades Borradas: " + toDelete.size());
            System.out.println("Roles Borrados: " + counterRoles);
            System.out.println("Personas De La Unidad Borradas: " + counterPersonal);
            System.out.println("---------------------------------------------");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Metodo para validar y descargar las personas
     */
    @Scheduled(fixedRate = 2000000)
    public void updateFunctionariesMDW() {
        try {
            String urlPerMDW = "http://integra.unibague.edu.co/functionariesChart/functionaries?api_token=";
            FunctionariesMDW[] res = restTemplate.getForObject(urlPerMDW + token, FunctionariesMDW[].class);
            List<FunctionariesMDW> response = List.of(res);

            //Procesar Data
            List<Personal> personalMDW = new ArrayList<>();
            List<Personal> personalBD = iPersonalRepository.getAllPersonal();
            List<Personal> newPersonal = new ArrayList<>();
            List<Personal> toDelete = new ArrayList<>();

            for (int i = 0; i < response.size(); i++) {

                Personal personal = new Personal();
                personal.setCedula(response.get(i).getIdentification());
                personal.setNombre(response.get(i).getName() + " " + response.get(i).getLast_name());
                personal.setCargo(response.get(i).getPosition());
                personal.setFoto(response.get(i).getDns_photo() + "/" + response.get(i).getDir_photo() + response.get(i).getId_photo());
                personal.setCorreo(response.get(i).getEmail());
                personal.setUnidad(response.get(i).getFaculty());
                personal.setTelefono("2760010");
                if (response.get(i).getExtension().equals("")) {
                    personal.setExtension(0);
                } else {
                    personal.setExtension(Integer.parseInt(response.get(i).getExtension()));
                }
                personal.setId_jerar(0);
                personal.setUnidad(response.get(i).getDep_code());
                personal.setOriginal("ORIGINAL");

                personalMDW.add(personal);
            }

            //Para borrar personas
            for (Personal personaBD : personalBD) {
                boolean encontrado = false;
                for (Personal personaMDW : personalMDW) {
                    if (personaBD.getCedula().equals(personaMDW.getCedula())) {
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    toDelete.add(personaBD);
                }
            }

            for (Personal personToDelete : toDelete) {
                iPersonalRepository.deleteByCedula(personToDelete.getCedula());
            }

            for (Personal personaMDW : personalMDW) {
                boolean existsInBD = false;

                for (Personal personaBD : personalBD) {

                    if (personaMDW.getCedula().equals(personaBD.getCedula())) {
                        existsInBD = true;

                        if (!personaMDW.getCargo().equals(personaBD.getCargo()) || !personaMDW.getExtension().equals(personaBD.getExtension()) || !personaMDW.getFoto().equals(personaBD.getFoto())) {
                            iPersonalRepository.updateMDWChangingValues(personaMDW.getCargo(), personaMDW.getExtension(), personaMDW.getFoto(), personaBD.getCedula());
                        }

                        if (personaBD.getOriginal().equals("ORIGINAL")) {
                            if (!personaMDW.getUnidad().equals(personaBD.getUnidad())) {
                                iPersonalRepository.updateOriginalUnidadMDW(personaMDW.getUnidad(), personaBD.getCedula());
                            }
                        }

                    }
                }

                if (!existsInBD) {
                    newPersonal.add(personaMDW);
                }
            }

            iPersonalRepository.saveAll(newPersonal);

            System.out.println("---------------------------------------------");
            System.out.println("Fecha: " + LocalDateTime.now());
            System.out.println("Personas Agregadas: " + newPersonal.size());
            System.out.println("Personas Borradas: " + toDelete.size());
            System.out.println("---------------------------------------------");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
