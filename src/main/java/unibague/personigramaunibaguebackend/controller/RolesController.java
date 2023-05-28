package unibague.personigramaunibaguebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibague.personigramaunibaguebackend.model.Roles;
import unibague.personigramaunibaguebackend.services.LoginService;
import unibague.personigramaunibaguebackend.services.RolesService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/roles")
@CrossOrigin("*")
public class RolesController {

    @Autowired
    private RolesService rolesService;

    @GetMapping("/saveRol/{id_jerar}/{nombre}/{unidad}")
    public ResponseEntity<String> getSaveRol(@PathVariable Integer id_jerar, @PathVariable String nombre, @PathVariable String unidad) throws Exception {
        try {
            rolesService.getAgregarRol(id_jerar, nombre, unidad);
            return ResponseEntity.ok("Rol Agregado Correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: Rol no agregado");
        }
    }

    @GetMapping("/getAllRolesByUnidad/{unidad}")
    public List<Roles> getAllRolesByUnidad(@PathVariable String unidad) throws Exception {
        List<Roles> roles = null;
        try {
            roles = rolesService.getAllRolesByUnidad(unidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }

    @GetMapping("/deleteRolById/{id}")
    public ResponseEntity<String> getDeleteRolById(@PathVariable Integer id) throws Exception {
        try {
            rolesService.getDeleteRolById(id);
            return ResponseEntity.ok("Rol Eliminado Correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: Rol no eliminado");
        }
    }

    @GetMapping("/updateNameById/{id}/{nombre}")
    public ResponseEntity<String> getUpdateNameById(@PathVariable Integer id , @PathVariable String nombre) throws Exception {
        try {
            rolesService.getUpdateNameById(id, nombre);
            return ResponseEntity.ok("Nombre actualizado Correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: Nombre no actualizado");
        }
    }
}
