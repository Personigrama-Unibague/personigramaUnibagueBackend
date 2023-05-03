package unibague.personigramaunibaguebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unibague.personigramaunibaguebackend.model.Unidades.Unidad;
import unibague.personigramaunibaguebackend.model.Unidades.Unidades;
import unibague.personigramaunibaguebackend.services.UnidadesService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/unidades")
@CrossOrigin("*")
public class UnidadesController {

    @Autowired
    private UnidadesService unidadesService;

    @GetMapping("/getUnidades")
    public List<Unidad> getUnidades() throws Exception{
        return unidadesService.getUnidades();
    }
}
