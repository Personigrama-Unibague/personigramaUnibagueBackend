package unibague.personigramaunibaguebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.services.PersonalService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/personal")
@CrossOrigin("*")
public class PersonalController {

    @Autowired
    private PersonalService personalService;

    @GetMapping("/getPersonasDistinct/{unidad}")
    public List<Personal> getPersonasDistinct(@PathVariable String unidad) throws Exception{
        return personalService.getPersonasDistinct(unidad);
    }

    @GetMapping("/getPersonal")
    public List<Personal> getPersonal() throws Exception{
        return personalService.getAllPersonas();
    }


    @GetMapping("/findPersonalByUnidad/{und}")
    public List<Personal> findPersonalByUnidad(@PathVariable String und) throws Exception{
        return personalService.getFindPersonalByUnidad(und);
    }

    @PostMapping("/agregarPersona")
    public ResponseEntity<String> agregarPersona(@RequestBody Personal personal) throws Exception{
        try{
            personalService.getAgregarPersona(personal);
            return ResponseEntity.ok("Persona Agregada Correctamente");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: Usuario no agregado");
        }

    }

    @GetMapping("/findPersonaById/{id}")
    public Personal findPersonaById(@PathVariable String id) throws Exception{
        return personalService.getFindPersonaById(id);
    }

    @GetMapping("/deletePersonaById/{id}/{unidad}")
    public ResponseEntity<String> deletePersonaById(@PathVariable String id, @PathVariable String unidad) throws Exception{
        try{
            personalService.getDeletePersonaById(id, unidad);
            return ResponseEntity.ok("Persona Eliminada Correctamente");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: Usuario no eliminado");
        }
    }

    @GetMapping("/guardarJson")
    public void guardarJson() throws Exception{
        personalService.guardarJson();
    }

}
