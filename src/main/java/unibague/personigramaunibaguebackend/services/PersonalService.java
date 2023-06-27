package unibague.personigramaunibaguebackend.services;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unibague.personigramaunibaguebackend.model.Json;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.model.Unidad;
import unibague.personigramaunibaguebackend.repository.IPersonalRepository;
import unibague.personigramaunibaguebackend.repository.IUnidadesRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Servicio que contiene los metodos para administrar la seccion de personal de la universidad

@Service
public class PersonalService {

    /**
     * Repositorio
     */
    @Autowired
    private IPersonalRepository iPersonalRepository;

    /**
     * Repositorio
     */
    @Autowired
    private IUnidadesRepository iUnidadesRepository;

    /**
     * Controlador para traer las personas unicas de tipo distinct por unidad
     *
     * @param unidad unidad seleccionada
     * @return Lista de personas
     */
    public List<Personal> getPersonasDistinct(String unidad) {
        List<Personal> personas = new ArrayList<>();

        try {
            personas = iPersonalRepository.getAllDistinct(unidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return personas;
    }

    /**
     * Controlador que trae el total de personal de la universidad
     *
     * @return Lista de personas
     */
    public List<Personal> getAllPersonas() {
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
    public void getSavePerson(Personal persona) {
        try {
            Personal personal = new Personal();

            personal.setCedula(persona.getCedula());
            personal.setNombre(persona.getNombre());
            personal.setCargo(persona.getCargo());
            personal.setCorreo(persona.getCorreo());
            personal.setTelefono(persona.getTelefono());
            personal.setExtension(persona.getExtension());
            personal.setFoto(persona.getFoto());
            personal.setUnidad(persona.getUnidad());
            personal.setId_jerar(0);
            personal.setOriginal("DUPLICADO");

            iPersonalRepository.save(personal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Borrar persona por cedula
     *
     * @param id cedula
     */
    public void getDeletePersonaById(String id, String unidad) {
        try {
            iPersonalRepository.deleteByCedulaAndUnidad(id, unidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Controlador que actualiza el Id_jerar de una persona
     *
     * @param id_jerar id de la persona
     * @param cedula   cedula de la persona
     * @param unidad   unidad a la que pertenece
     * @return Mensaje
     */
    public void getUpdateIdJerarByCedulaUnd(Integer id_jerar, String cedula, String unidad) {
        try {
            iPersonalRepository.updateIdJerarByCedulaUnd(id_jerar, cedula, unidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Controlador que actualiza una persona a su id_jerar a default
     *
     * @param cedula cedula de la persona
     * @param unidad unidad a la que pertenece
     * @return Mensaje
     */
    public void getUpdateIdJerarDefault(String cedula, String unidad) {
        try {
            iPersonalRepository.updateIdJerarDefault(cedula, unidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para actualizar el Id_jerar de todas las personas de una unidad
     *
     * @param unidad   unidad a la que pertenece
     * @param id_jerar id_jerar a actualizar
     * @return Mensaje
     */
    public void getUpdateIdJerarDefaultAllSection(String unidad, Integer id_jerar) {
        try {
            iPersonalRepository.updateIdJerarDefaultAllSection(unidad, id_jerar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
