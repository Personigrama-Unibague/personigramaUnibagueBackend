package unibague.personigramaunibaguebackend.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibague.personigramaunibaguebackend.services.LoginService;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<?>> getFindAllUsers() throws Exception {
        List<?> list = new ArrayList<>();
        try {
            list = loginService.getFindAllUsers();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(list);
        }
    }

    /**
     * Metodo para autenticarse en la aplicacion con JWT
     *
     * @param credentials
     * @return ResponseEntity con Jwt o Error
     */
    @PostMapping("/loginAuthentication")
    public ResponseEntity<String> loginAuthentication(@RequestBody Map<String, String> credentials) {
        String user = credentials.get("user");
        String password = credentials.get("password");

        Boolean response = loginService.getLoginAuthenticationService(user, password);

        if (response) {
            String jwt = generateJWT(user);
            return ResponseEntity.ok(jwt);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
        }
    }

    /**
     * Metodo para Generar el JWT
     *
     * @param username
     * @return Codigo JWT
     */
    private String generateJWT(String username) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String jwt = Jwts.builder()
                .setSubject(username)
                .signWith(key)
                .compact();
        return jwt;
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
    public ResponseEntity<String> getDeleteUser(@PathVariable Integer id) throws Exception {
        try {
            loginService.getDeleteUser(id);
            return ResponseEntity.ok("Persona Eliminada Correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: Persona No Eliminada Correctamente");
        }
    }
}
