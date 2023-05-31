package unibague.personigramaunibaguebackend.services;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unibague.personigramaunibaguebackend.model.Login;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.model.ResumeLogin;
import unibague.personigramaunibaguebackend.repository.ILoginRepository;
import unibague.personigramaunibaguebackend.repository.IPersonalRepository;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Servicio que contiene los metodos para administrar la seccion de usuarios

@Service
public class LoginService {

    /**
     * Repositorio
     */
    @Autowired
    private ILoginRepository iLoginRepository;

    /**
     * Servicio para traer los usuarios
     *
     * @return Lista de usuarios
     * @throws Exception
     */
    public List<?> getFindAllUsers() {
        try {
            List<Login> list = iLoginRepository.findAllUsers();
            List<ResumeLogin> finalLogin = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {

                ResumeLogin objectLogin = new ResumeLogin();
                objectLogin.setId(list.get(i).getId());
                objectLogin.setUsuario(list.get(i).getUsuario());

                finalLogin.add(objectLogin);
            }
            return finalLogin;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metodo para el loggeo para la seccion de administracion
     *
     * @param user     usuario
     * @param password contraseña
     * @return True or False
     */
    public Boolean getLoginAuthenticationService(String user, String password) {
        try {
            return iLoginRepository.loginAuthentication(user, password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Servicio para guardar un nuevo usuario
     *
     * @param user     usuario
     * @param password contraseña
     * @return Respuesta
     * @throws Exception
     */
    public void getSaveNewUser(String user, String password) {
        try {
            Date fechaActual = new Date();
            iLoginRepository.saveNewUser(password, fechaActual, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Servicio para eliminar un usuario
     *
     * @param id id del usuario
     * @throws Exception
     */
    public void getDeleteUser(Integer id) {
        try {
            iLoginRepository.deleteUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
