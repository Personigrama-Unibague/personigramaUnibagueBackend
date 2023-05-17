package unibague.personigramaunibaguebackend.services;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Service;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.model.Unidad;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            personas = objectMapper.readValue(new File("./src/main/resources/static/personal.json"), new TypeReference<List<Personal>>() {
            });

            for (int i = 0; i < personas.size(); i++) {
                if (personas.get(i).getUnidad().equals(id)) {
                    personasByUnidad.add(personas.get(i));
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

    public void getAgregarPersona(Personal persona) {
        List<Personal> personas = null;
        String ruta = "./src/main/resources/static/personal.json";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

            personas = objectMapper.readValue(new File(ruta), new TypeReference<List<Personal>>() {});
            personas.add(persona);

            String json = objectMapper.writeValueAsString(personas);
            Files.writeString(Paths.get(ruta), json);

        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
