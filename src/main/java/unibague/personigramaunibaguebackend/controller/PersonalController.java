package unibague.personigramaunibaguebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.model.Unidad;
import unibague.personigramaunibaguebackend.services.PersonalService;

import java.util.ArrayList;
import java.util.List;

//Controlador que contiene los metodos para administrar la seccion de personal de la universidad

@RestController
@RequestMapping(value = "/api/v1/personal")
@CrossOrigin("*")
public class PersonalController {

    //Servicio para administrar la seccion de personal de la universidad
    @Autowired
    private PersonalService personalService;

    /**
     * Controlador para traer las personas unicas de tipo distinct por unidad
     *
     * @param unidad unidad seleccionada
     * @return Lista de personas
     * @throws Exception
     */
    @GetMapping("/getPersonasDistinct/{unidad}")
    public List<Personal> getPersonasDistinct(@PathVariable String unidad) throws Exception {
        List<Personal> list = new ArrayList<>();
        try {
            list = personalService.getPersonasDistinct(unidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Controlador que trae el total de personal de la universidad
     *
     * @return Lista de personas
     * @throws Exception
     */
    @GetMapping("/getPersonal")
    public List<Personal> getAllPersonas() throws Exception {
        List<Personal> list = new ArrayList<>();
        try {
            list = personalService.getAllPersonas();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Controlador que busca las personas por la unidad
     *
     * @param und unidad
     * @return Lista de personas
     * @throws Exception
     */
    @GetMapping("/findPersonalByUnidad/{und}")
    public List<Personal> getFindPersonalByUnidad(@PathVariable String und) throws Exception {
        List<Personal> list = new ArrayList<>();
        try {
            list = personalService.getFindPersonalByUnidad(und);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Controlador que agrega una persona a la lista de personal de la universidad
     *
     * @param personal Objecto de tipo persona
     * @return Mensaje
     * @throws Exception
     */
    @PostMapping("/savePersona")
    public ResponseEntity<String> getSavePerson(@RequestBody Personal personal) throws Exception {
        try {
            personalService.getSavePerson(personal);
            return ResponseEntity.ok("Persona Agregada Correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: Usuario no agregado");
        }
    }

    /**
     * Controlador que busca personas por id
     *
     * @param id id de la persona
     * @return Objecto persona
     * @throws Exception
     */
    @GetMapping("/findPersonaById/{id}")
    public Personal getFindPersonaById(@PathVariable String id) throws Exception {
        Personal persona = new Personal();
        try {
            persona = personalService.getFindPersonaById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return persona;
    }

    /**
     * Controlador que elimina persona por id
     *
     * @param id     id de la persona
     * @param unidad unidad a la cual pertenece la persona
     * @return Mensaje
     * @throws Exception
     */
    @GetMapping("/deletePersonaById/{id}/{unidad}")
    public ResponseEntity<String> getDeletePersonaById(@PathVariable String id, @PathVariable String unidad) throws Exception {
        try {
            personalService.getDeletePersonaById(id, unidad);
            return ResponseEntity.ok("Persona Eliminada Correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: Usuario no eliminado");
        }
    }

    /**
     * Controlador que actualiza el Id_jerar de una persona
     *
     * @param id_jerar id de la persona
     * @param cedula   cedula de la persona
     * @param unidad   unidad a la que pertenece
     * @return Mensaje
     * @throws Exception
     */
    @GetMapping("/updateIdJerarByCedulaUnd/{id_jerar}/{cedula}/{unidad}")
    public ResponseEntity<String> getUpdateIdJerarByCedulaUnd(@PathVariable Integer id_jerar, @PathVariable String cedula, @PathVariable String unidad) throws Exception {
        try {
            personalService.getUpdateIdJerarByCedulaUnd(id_jerar, cedula, unidad);
            return ResponseEntity.ok("Persona actualizada Correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: persona no actualizada");
        }
    }

    /**
     * Controlador que actualiza una persona a su id_jerar a default
     *
     * @param cedula cedula de la persona
     * @param unidad unidad a la que pertenece
     * @return Mensaje
     * @throws Exception
     */
    @GetMapping("/updateIdJerarByCedulaUnd/{cedula}/{unidad}")
    public ResponseEntity<String> getUpdateIdJerarDefault(@PathVariable String cedula, @PathVariable String unidad) throws Exception {
        try {
            personalService.getUpdateIdJerarDefault(cedula, unidad);
            return ResponseEntity.ok("Persona actualizada Correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: persona no actualizada");
        }
    }

    /**
     * Controlador para actualizar el Id_jerar de todas las personas de una unidad
     *
     * @param unidad   unidad a la que pertenece
     * @param id_jerar id_jerar a actualizar
     * @return Mensaje
     * @throws Exception
     */
    @GetMapping("/updateIdJerarDefaultAllSection/{unidad}/{id_jerar}")
    public ResponseEntity<String> getUpdateIdJerarDefaultAllSection(@PathVariable String unidad, @PathVariable Integer id_jerar) throws Exception {
        try {
            personalService.getUpdateIdJerarDefaultAllSection(unidad, id_jerar);
            return ResponseEntity.ok("Persona actualizada Correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: persona no actualizada");
        }
    }

    //----------------------------------------------------------------------------------------------------------

    /**
     * Metodo para pasar informacion de Json a Base De Datos
     *
     * @return Respueta HTTP
     * @throws Exception
     */
    @GetMapping("/saveJson")
    public ResponseEntity<String> getSaveJson() throws Exception {
        try {
            personalService.guardarJson();
            return ResponseEntity.ok("Json Cargado Correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error");
        }
    }

}
