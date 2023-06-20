package unibague.personigramaunibaguebackend.model;

import lombok.*;

import javax.persistence.*;

//Modelo que compone la estructura del personal de la universidad

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "personal")
public class Personal {

    //Id generado en base de datos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private String id;

    //Cedula de la persona
    @Getter
    @Setter
    private String cedula;

    //Nombre de la persona
    @Getter
    @Setter
    private String nombre;

    //Cargo de la persona
    @Getter
    @Setter
    private String cargo;

    //Correo de la persona
    @Getter
    @Setter
    private String correo;

    //Telefono de la persona
    @Getter
    @Setter
    private String telefono;

    //Extension de la persona
    @Getter
    @Setter
    private Integer extension;

    //Foto de la persona
    @Getter
    @Setter
    private String foto;

    //Unidad a la que pertenece la persona
    @Getter
    @Setter
    private String unidad;

    //Id del rol al que pertenece la persona
    @Getter
    @Setter
    private Integer id_jerar;
}
