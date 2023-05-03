package unibague.personigramaunibaguebackend.services;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import unibague.personigramaunibaguebackend.model.Unidades.Unidad;
import unibague.personigramaunibaguebackend.model.Unidades.Unidades;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class UnidadesService {

    public List<Unidad> getUnidades() {
        List<Unidad> unidades = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
             unidades = objectMapper.readValue(new File("./src/main/resources/static/unidades.json"), new TypeReference<List<Unidad>>() {
            });
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return unidades;
    }

}
