package unibague.personigramaunibaguebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibague.personigramaunibaguebackend.model.Unidad;
import unibague.personigramaunibaguebackend.services.UnidadesService;

import java.util.ArrayList;
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
        List<Unidad> list = new ArrayList<>();
        try {
            list = unidadesService.getUnidades();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
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
        String unidad = "";
        try {
            unidad = unidadesService.getUnidadNameById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unidad;
    }

    /**
     * Metodo para pasar informacion de Json a Base De Datos
     *
     * @throws Exception
     */
    @GetMapping("/saveJson")
    public ResponseEntity<String> getSaveJson() throws Exception {
        try {
            unidadesService.getSaveJson();
            return ResponseEntity.ok("Json Cargado Correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error");
        }
    }

}
