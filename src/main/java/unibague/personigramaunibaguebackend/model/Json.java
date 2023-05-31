package unibague.personigramaunibaguebackend.model;

import lombok.*;

import javax.persistence.*;

//Modelo que mapea el Json para la tabla personal

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "personal")
public class Json {

    //Nombre de la persona
    @Id
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

    //Cedula de la persona
    @Getter
    @Setter
    private String cedula;

    //Unidad a la que pertenece la persona
    @Getter
    @Setter
    private String unidad;
}
