package unibague.personigramaunibaguebackend.model;

import lombok.*;

import javax.persistence.*;

//Modelo que mapea los roles de las unidades

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "roles")
public class Roles {

    //Id generado en base de datos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Getter
    @Setter
    private String id;

    //Id Jerarquico en orden numerico
    @Column(nullable = false)
    @Getter
    @Setter
    private Integer id_jerar;

    //Nombre de la seccion
    @Column(nullable = false)
    @Getter
    @Setter
    private String nombre;

    //Unidad a la que pertenece el rol
    @Column(nullable = false)
    @Getter
    @Setter
    private String unidad;

}
