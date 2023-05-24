package unibague.personigramaunibaguebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.services.LoginService;
import unibague.personigramaunibaguebackend.services.PersonalService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/login")
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/loginAuthentication/{user}/{password}")
    public ResponseEntity<Boolean> getPersonal(@PathVariable String user, @PathVariable String password) throws Exception{
        try{
            return ResponseEntity.ok(loginService.loginAuthenticationService(user,password));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(false);
        }
    }
}
