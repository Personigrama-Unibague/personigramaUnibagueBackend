package unibague.personigramaunibaguebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.model.ResumeLogin;
import unibague.personigramaunibaguebackend.services.LoginService;
import unibague.personigramaunibaguebackend.services.PersonalService;

import java.util.List;

//Controlador que contiene los metodos para administrar la seccion de usuarios

@RestController
@RequestMapping(value = "/api/v1/login")
@CrossOrigin("*")
public class LoginController {

    //Servicio para administrar la seccion de usuarios
    @Autowired
    private LoginService loginService;

    /**
     * Controlador para traer los usuarios
     *
     * @return Lista de usuarios
     * @throws Exception
     */
    @GetMapping("/findAllUsers")
    public List<?> getFindAllUsers() throws Exception {
        return loginService.getFindAllUsers();
    }

    /**
     * Controlador para el loggeo para la seccion de administracion
     *
     * @param user     usuario
     * @param password contraseña
     * @return True or False
     * @throws Exception false
     */
    @GetMapping("/loginAuthentication/{user}/{password}")
    public ResponseEntity<Boolean> getLoginAuthentication(@PathVariable String user, @PathVariable String password) throws Exception {

        Boolean response = loginService.getLoginAuthenticationService(user, password);

        if (response == true) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    /**
     * Controlador para guardar un nuevo usuario
     *
     * @param user     usuario
     * @param password contraseña
     * @return Respuesta
     * @throws Exception
     */
    @GetMapping("/saveNewUser/{user}/{password}")
    public ResponseEntity<String> getSaveNewUser(@PathVariable String user, @PathVariable String password) throws Exception {

        try {
            loginService.getSaveNewUser(user, password);
            return ResponseEntity.ok("Persona Agregada Correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: persona no agregada");
        }
    }

    /**
     * Controlador para eliminar un usuario
     *
     * @param id id del usuario
     * @throws Exception
     */
    @GetMapping("/deleteUser/{id}")
    public void getDeleteUser(@PathVariable Integer id) throws Exception {
        loginService.getDeleteUser(id);
    }
}
