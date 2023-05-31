package unibague.personigramaunibaguebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import unibague.personigramaunibaguebackend.model.Unidad;
import unibague.personigramaunibaguebackend.services.UnidadesService;

import java.util.List;

//Controlador que contiene los metodos para administrar las unidades

@RestController
@RequestMapping(value = "/api/v1/unidades")
@CrossOrigin("*")
public class UnidadesController {

    //Servicio para administrar las unidades
    @Autowired
    private UnidadesService unidadesService;

    /**
     * Controlador para traer las unidades
     *
     * @return Lista de unidades
     * @throws Exception
     */
    @GetMapping("/getUnidades")
    public List<Unidad> getUnidades() throws Exception {
        return unidadesService.getUnidades();
    }

    /**
     * Controlador para traer la unidad por id
     *
     * @param id de la unidad
     * @return Nombre
     * @throws Exception
     */
    @GetMapping("/getUnidadNameById/{id}")
    public String getUnidadNameById(@PathVariable String id) throws Exception {
        return unidadesService.getUnidadNameById(id);
    }

    /**
     * Metodo para pasar informacion de Json a Base De Datos
     *
     * @throws Exception
     */
    @GetMapping("/guardarJson")
    public void guardarJson() throws Exception {
        try {
            unidadesService.guardarJson();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
