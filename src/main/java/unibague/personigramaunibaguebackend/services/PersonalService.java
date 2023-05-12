package unibague.personigramaunibaguebackend.services;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.model.Unidad;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonalService {

    public List<Personal> getPersonas() {
        List<Personal> personas = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            personas = objectMapper.readValue(new File("./src/main/resources/static/personal.json"), new TypeReference<List<Personal>>() {
            });
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return personas;
    }

    public List<Personal> getPersonasByUnidad(String id) {
        List<Personal> personas = null;
        List<Personal> personasByUnidad = new ArrayList<>();
        Personal persona = new Personal();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            personas = objectMapper.readValue(new File("./src/main/resources/static/personal.json"), new TypeReference<List<Personal>>() {});

            for(int i = 0; i < personas.size(); i++){
                if(personas.get(i).getUnidad().equals(id)){
                    persona.setNombre(personas.get(i).getNombre());
                    persona.setCargo(personas.get(i).getCargo());
                    persona.setCorreo(personas.get(i).getCorreo());
                    persona.setTelefono(personas.get(i).getTelefono());
                    persona.setExtension(personas.get(i).getExtension());
                    persona.setFoto(personas.get(i).getFoto());
                    persona.setUnidad(personas.get(i).getUnidad());

                    personasByUnidad.add(persona);
                }
            }
            return personasByUnidad;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            return null;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
