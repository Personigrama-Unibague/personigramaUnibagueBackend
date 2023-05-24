package unibague.personigramaunibaguebackend.services;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.repository.ILoginRepository;
import unibague.personigramaunibaguebackend.repository.IPersonalRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    private ILoginRepository iLoginRepository;

    /**
     * Metodo para obtener todas las personas
     * @return Lista de personas
     */
    public Boolean loginAuthenticationService(String user, String password ) {
        try {
            return iLoginRepository.loginAuthentication(user, password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}