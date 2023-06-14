package unibague.personigramaunibaguebackend.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//Modelo que mapea las unidades

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "unidades_2")
public class Unidad {

    //Id generado en base de datos
    @Id
    @Getter
    @Setter
    private String id;

    //Nombre de la unidad
    @Getter
    @Setter
    private String nombre;

    //Rol de la unidad
    @Getter
    @Setter
    private String parent_id;

}
