package unibague.personigramaunibaguebackend.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//Modelo que mapea las unidades

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DependenciesMDW {

    //Id generado en base de datos
    private String dep_code;

    //Id generado en base de datos
    private String dep_name;

    //Id generado en base de datos
    private String dep_father;

    //Id generado en base de datos
    private String dep_nom_father;

}
