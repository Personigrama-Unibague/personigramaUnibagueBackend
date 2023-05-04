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

}
