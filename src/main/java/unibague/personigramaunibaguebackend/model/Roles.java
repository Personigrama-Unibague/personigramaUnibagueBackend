package unibague.personigramaunibaguebackend.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Getter @Setter private String id;

    @Column(nullable = false)
    @Getter @Setter private String id_jerar;

    @Column(nullable = false)
    @Getter @Setter private String nombre;

    @Column(nullable = false)
    @Getter @Setter private String unidad;

}
