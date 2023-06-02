package unibague.personigramaunibaguebackend.services;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.model.Unidad;
import com.fasterxml.jackson.core.type.TypeReference;
import unibague.personigramaunibaguebackend.repository.IUnidadesRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

//Servicio que contiene los metodos para administrar las unidades

@Service
public class UnidadesService {

    /**
     * Repositorio
     */
    @Autowired
    private IUnidadesRepository iUnidadesRepository;

    /**
     * Metodo para traer todas las unidades
     *
     * @return lista de unidades
     */
    public List<Unidad> getUnidades() {
        List<Unidad> unidades = null;

        try {
            unidades = iUnidadesRepository.getAllUnidades();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unidades;
    }

    /**
     * Traer unidades por id
     *
     * @param id id unidad
     * @return Nombre unidad
     */
    public String getUnidadNameById(String id) {
        try {
            return iUnidadesRepository.getNameById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metodo para pasar informacion de Json a Base De Datos
     */
    public void getSaveJson() {
        List<Unidad> unidades = null;
        Personal unidad = new Personal();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            unidades = objectMapper.readValue(new File("./src/main/resources/static/unidades.json"), new TypeReference<List<Unidad>>() {
            });
            iUnidadesRepository.saveAll(unidades);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
