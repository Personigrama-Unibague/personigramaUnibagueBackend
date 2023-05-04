package unibague.personigramaunibaguebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.model.Unidad;
import unibague.personigramaunibaguebackend.services.PersonalService;
import unibague.personigramaunibaguebackend.services.UnidadesService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/personal")
@CrossOrigin("*")
public class PersonalController {

    @Autowired
    private PersonalService personalService;

    @GetMapping("/getPersonal")
    public List<Personal> getPersonal() throws Exception{
        return personalService.getPersonas();
    }
}
