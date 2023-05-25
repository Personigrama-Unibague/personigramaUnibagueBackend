package unibague.personigramaunibaguebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibague.personigramaunibaguebackend.services.LoginService;
import unibague.personigramaunibaguebackend.services.RolesService;

@RestController
@RequestMapping(value = "/api/v1/roles")
@CrossOrigin("*")
public class RolesController {

    @Autowired
    private RolesService rolesService;


    @GetMapping("/crearRol/{id_jerar}/{nombre}/{unidad}")
    public ResponseEntity<String> getCrearRol(@PathVariable Integer id_jerar, @PathVariable String nombre, @PathVariable String unidad) throws Exception {
        try{
            rolesService.agregarRol(id_jerar,nombre,unidad);
            return ResponseEntity.ok("Rol Agregado Correctamente");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: Rol no agregado");
        }
    }
}
