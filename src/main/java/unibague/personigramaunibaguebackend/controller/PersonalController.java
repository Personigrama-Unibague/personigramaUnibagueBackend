package unibague.personigramaunibaguebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.services.PersonalService;

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
        return personalService.getPersonasDistinct(unidad);
    }

    /**
     * Controlador que trae el total de personal de la universidad
     *
     * @return Lista de personas
     * @throws Exception
     */
    @GetMapping("/getPersonal")
    public List<Personal> getAllPersonas() throws Exception {
        return personalService.getAllPersonas();
    }

    /**
     * Controlador que busca las personas por la unidad
     *
     * @param und unidad
     * @return Lista de personas
     * @throws Exception
     */
    @GetMapping("/findPersonalByUnidad/{und}")
    public List<Personal> findPersonalByUnidad(@PathVariable String und) throws Exception {
        return personalService.getFindPersonalByUnidad(und);
    }

    /**
     * Controlador que agrega una persona a la lista de personal de la universidad
     *
     * @param personal Objecto de tipo persona
     * @return Mensaje
     * @throws Exception
     */
    @PostMapping("/agregarPersona")
    public ResponseEntity<String> agregarPersona(@RequestBody Personal personal) throws Exception {
        try {
            personalService.getAgregarPersona(personal);
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
    public Personal findPersonaById(@PathVariable String id) throws Exception {
        return personalService.getFindPersonaById(id);
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
    public ResponseEntity<String> deletePersonaById(@PathVariable String id, @PathVariable String unidad) throws Exception {
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
     * @param id_jerar     id de la persona
     * @param cedula cedula de la persona
     * @param unidad unidad a la que pertenece
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
     * @throws Exception
     */
    @GetMapping("/guardarJson")
    public void guardarJson() throws Exception {
        try {
            personalService.guardarJson();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
