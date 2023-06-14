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
import unibague.personigramaunibaguebackend.repository.IUnidadesRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@EnableScheduling
public class MiddlewareJob {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private IUnidadesRepository iUnidadesRepository;

    @Autowired
    private IPersonalRepository iPersonalRepository;

    private String token = "$2y$10$s/5xSDieUMEvYD/gfNqFAeFzvWXt13jhWuugpJzQ9rZQrbGpBYUxi";

    @Scheduled(fixedRate = 15000)
    public void updateDependenciesMDW() {

        String urlUndMDW = "http://integra.unibague.edu.co/functionariesChart/dependencies?api_token=";
        DependenciesMDW[] res = restTemplate.getForObject(urlUndMDW + token, DependenciesMDW[].class);
        List<DependenciesMDW> response = List.of(res);

        //Procesar Data
        List<Unidad> unidadesMDW = new ArrayList<>();
        List<Unidad> unidadesBD = iUnidadesRepository.getAllUnidades();
        List<Unidad> newUnidades = new ArrayList<>();

        for (int i = 0; i < response.size(); i++) {
            Unidad unidad = new Unidad();
            unidad.setId(response.get(i).getDep_code());
            unidad.setNombre(response.get(i).getDep_name());
            unidad.setParent_id(response.get(i).getDep_father());

            unidadesMDW.add(unidad);
        }

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
        System.out.println("Unidades Agregadas: " + newUnidades.size());
        System.out.println("Ejecutado");
    }

    @Scheduled(fixedRate = 2000000)
    public void updateFunctionariesMDW() {

        String urlPerMDW = "http://integra.unibague.edu.co/functionariesChart/functionaries?api_token=";
        FunctionariesMDW[] res = restTemplate.getForObject(urlPerMDW + token, FunctionariesMDW[].class);
        List<FunctionariesMDW> response = List.of(res);

        //Procesar Data
        List<Personal> personalMDW = new ArrayList<>();
        List<Personal> personalBD = iPersonalRepository.findAll();
        List<Personal> newPersonal = new ArrayList<>();

        for (int i = 0; i < response.size(); i++) {
            Personal personal = new Personal();
            personal.setCedula(response.get(i).getIdentification());
            personal.setNombre(response.get(i).getName() + " " +response.get(i).getLast_name());
            personal.setCargo("CAMBIAR");
            personal.setFoto(response.get(i).getDns_photo() + "/" + response.get(i).getDir_photo() + response.get(i).getId_photo());
            personal.setCorreo(response.get(i).getEmail());
            personal.setUnidad(response.get(i).getFaculty());
            personal.setTelefono("CAMBIAR");
            personal.setExtension(00000);
            personal.setId_jerar(0);
            personal.setUnidad("CAMBIAR");

            personalMDW.add(personal);
        }

        for (Personal MDW : personalMDW) {
            boolean encontrado = false;
            for (Personal BD : personalBD) {
                if (BD.getId().equals(MDW.getId())) {
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                newPersonal.add(MDW);
            }
        }

        iPersonalRepository.saveAll(newPersonal);
        System.out.println("Personas Agregadas: " + newPersonal.size());
        System.out.println("Ejecutado");
    }
}
