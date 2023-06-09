package unibague.personigramaunibaguebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibague.personigramaunibaguebackend.model.Roles;
import unibague.personigramaunibaguebackend.services.LoginService;
import unibague.personigramaunibaguebackend.services.RolesService;

import java.util.List;

//Controlador que contiene los metodos para administrar los roles

@RestController
@RequestMapping(value = "/api/v1/roles")
@CrossOrigin("*")
public class RolesController {

    //Servicio para administrar la seccion de roles
    @Autowired
    private RolesService rolesService;

    /**
     * Controlador para guardar roles
     *
     * @param id_jerar Id_jerar de los roles
     * @param nombre   Nombre del rol
     * @param unidad   Unidad a la que va a pertenecer el rol
     * @return Mensaje
     * @throws Exception
     */
    @GetMapping("/saveRol/{id_jerar}/{nombre}/{unidad}")
    public ResponseEntity<String> getSaveRol(@PathVariable Integer id_jerar, @PathVariable String nombre, @PathVariable String unidad) throws Exception {
        try {
            rolesService.getSaveRol(id_jerar, nombre, unidad);
            return ResponseEntity.ok("Rol Agregado Correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: Rol no agregado");
        }
    }

    /**
     * Controlador para traer los roles por unidad
     *
     * @param unidad unidad a la que pertenecen los roles
     * @return Lista de roles
     * @throws Exception
     */
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

    /**
     * Controlador para borrar un rol por id
     *
     * @param id Id del rol
     * @return Mensaje
     * @throws Exception
     */
    @GetMapping("/deleteRolById/{id}/{unidad}")
    public ResponseEntity<String> getDeleteRolById(@PathVariable Integer id, @PathVariable String unidad) throws Exception {
        try {
            rolesService.getDeleteRolById(id, unidad);
            return ResponseEntity.ok("Rol Eliminado Correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: Rol no eliminado");
        }
    }

    /**
     * Controlador para actualizar el nombre de un rol
     *
     * @param id     id del rol
     * @param nombre nuevo nombre del rol
     * @return Mensaje
     * @throws Exception
     */
    @GetMapping("/updateNameById/{id}/{nombre}")
    public ResponseEntity<String> getUpdateNameById(@PathVariable Integer id, @PathVariable String nombre) throws Exception {
        try {
            rolesService.getUpdateNameById(id, nombre);
            return ResponseEntity.ok("Nombre actualizado Correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: Nombre no actualizado");
        }
    }

    /**
     * Controlador para cambiar el id_jerar de un rol y actualizar el resto
     *
     * @param antiguo antiguo id_jerar del rol
     * @param nuevo   nuevo id_jerar del rol
     * @param unidad  unidad del rol
     */
    @GetMapping("/updateIdJerarRol/{antiguo}/{nuevo}/{unidad}")
    public ResponseEntity<String> getUpdateIdJerarRol(@PathVariable String antiguo, @PathVariable String nuevo, @PathVariable String unidad) throws Exception {
        try {
            rolesService.getUpdateIdJerarRol(antiguo, nuevo, unidad);
            return ResponseEntity.ok("Rol actualizado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: Rol no actualizado");
        }
    }
}
