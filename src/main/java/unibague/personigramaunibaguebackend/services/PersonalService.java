package unibague.personigramaunibaguebackend.services;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.repository.IPersonalRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonalService {

    @Autowired
    private IPersonalRepository iPersonalRepository;

    /**
     * Metodo para obtener todas las personas
     * @return Lista de personas
     */
    public List<Personal> getPersonas() {
        List<Personal> personas = null;

        try {
           personas = iPersonalRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return personas;
    }

    /**
     * Metodo para traer todas las personas de una unidad
     * @param und unidad
     * @return Lista de personas
     */
    public List<Personal> getFindPersonalByUnidad(String und) {
        List<Personal> personas = null;

        try {
            personas = iPersonalRepository.findByUnidad(und);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return personas;
    }

    /**
     * Metodo para encontrar persona por cedula
     * @param id cedula
     * @return Persona buscada
     */
    public Personal getFindPersonaById(String id) {
        Personal persona = new Personal();

        try {
            persona =  iPersonalRepository.findByCedula(id);
            return persona;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metodo para agregar una persona
     * @param persona persona a agregar
     */
    public void getAgregarPersona(Personal persona) {
        List<Personal> personas = null;
        try {
            iPersonalRepository.save(persona);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Borrar persona por cedula
     * @param id cedula
     */
    public void getDeletePersonaById(String id) {
        try {
            iPersonalRepository.deleteByCedula(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para guardar el json en la base de datos
     */
    public void guardarJson() {
        List<Personal> personas = null;
        Personal persona = new Personal();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            personas = objectMapper.readValue(new File("./src/main/resources/static/personal.json"), new TypeReference<List<Personal>>() {
            });
            iPersonalRepository.saveAll(personas);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
