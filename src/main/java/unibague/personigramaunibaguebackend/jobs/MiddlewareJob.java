package unibague.personigramaunibaguebackend.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import unibague.personigramaunibaguebackend.model.DependenciesMDW;
import unibague.personigramaunibaguebackend.model.Unidad;
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

    @Scheduled(fixedRate = 15000)
    public void updateDependenciesMDW() {
        String token = "$2y$10$s/5xSDieUMEvYD/gfNqFAeFzvWXt13jhWuugpJzQ9rZQrbGpBYUxi";
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
}
