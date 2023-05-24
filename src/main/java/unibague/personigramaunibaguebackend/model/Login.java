package unibague.personigramaunibaguebackend.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="login")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Getter @Setter private String id;

    @Column(nullable = false)
    @Getter @Setter private String usuario;

    @Column(nullable = false)
    @Getter @Setter private String contraseña;

    @Column(nullable = false)
    @Getter @Setter private Date fecha_creacion;

    @PrePersist
    public void prePersist() {
        fecha_creacion = new Date();
    }
}
