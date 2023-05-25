package unibague.personigramaunibaguebackend.services;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unibague.personigramaunibaguebackend.model.Json;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.model.Unidad;
import unibague.personigramaunibaguebackend.repository.IPersonalRepository;
import unibague.personigramaunibaguebackend.repository.IRolesRepository;
import unibague.personigramaunibaguebackend.repository.IUnidadesRepository;

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

    @Autowired
    private IUnidadesRepository iUnidadesRepository;

    /**
     * Metodo para obtener todas las personas
     *
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
     *
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
     *
     * @param id cedula
     * @return Persona buscada
     */
    public Personal getFindPersonaById(String id) {
        Personal persona = new Personal();

        try {
            persona = iPersonalRepository.findByCedula(id);
            return persona;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metodo para agregar una persona
     *
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
     *
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

        List<Json> json = null;
        List<Personal> personal = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.readValue(new File("./src/main/resources/static/personal.json"), new TypeReference<List<Json>>() {
            });


            for (int i = 0; i < json.size(); i++) {

                Boolean existe = iPersonalRepository.existByCedula(json.get(i).getCedula());
                if (!existe) {
                    //Primero Crear el objeto unidad
                    Personal persona = new Personal();
                    Unidad und = iUnidadesRepository.getUndById(json.get(i).getUnidad());

                    //Primero creo el objeto Personal
                    persona.setCedula(json.get(i).getCedula());
                    persona.setNombre(json.get(i).getNombre());
                    persona.setCargo(json.get(i).getCargo());
                    persona.setCorreo(json.get(i).getCorreo());
                    persona.setTelefono(json.get(i).getTelefono());
                    persona.setExtension(json.get(i).getExtension());
                    persona.setFoto(json.get(i).getFoto());
                    persona.setUnidad(und);
                    persona.setId_jerar(0);
                    personal.add(persona);
                }
            }

            iPersonalRepository.saveAll(personal);

        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
