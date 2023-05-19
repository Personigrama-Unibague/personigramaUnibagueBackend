package unibague.personigramaunibaguebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import unibague.personigramaunibaguebackend.model.Unidad;
import unibague.personigramaunibaguebackend.services.UnidadesService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/unidades")
@CrossOrigin("*")
public class UnidadesController {

    @Autowired
    private UnidadesService unidadesService;

    @GetMapping("/getUnidades")
    public List<Unidad> getUnidades() throws Exception{
        return unidadesService.getUnidades();
    }

    @GetMapping("/getUnidadNameById/{id}")
    public String getUnidadNameById(@PathVariable String id) throws Exception{
        return unidadesService.getUnidadNameById(id);
    }

    @GetMapping("/guardarJson")
    public void guardarJson() throws Exception{
        unidadesService.guardarJson();
    }

}
