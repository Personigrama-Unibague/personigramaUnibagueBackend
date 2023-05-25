package unibague.personigramaunibaguebackend.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="personal")
public class Json {

    @Id
    @Getter @Setter private String nombre;

    @Getter @Setter private String cargo;

    @Getter @Setter private String correo;

    @Getter @Setter private String telefono;

    @Getter @Setter private Integer extension;

    @Getter @Setter private String foto;

    @Getter @Setter private String cedula;

    @Getter @Setter private String unidad;
}
