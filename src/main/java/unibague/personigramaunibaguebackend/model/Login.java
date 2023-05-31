package unibague.personigramaunibaguebackend.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

//Modelo que compone la estructura para el loggeo de la aplicacion

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="login")
public class Login {

    //Id generado en base de datos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Getter @Setter private String id;

    //Usuario para loggeo
    @Column(nullable = false)
    @Getter @Setter private String usuario;

    //Contraseña para loggeo
    @Column(nullable = false)
    @Getter @Setter private String contrasena;

    //Fecha de creacion del registro
    @Column(nullable = false)
    @Getter @Setter private Date fecha_creacion;

    //Metodo para actualizar el valor de la fecha de creacion a la fecha actual
    @PrePersist
    public void prePersist() {
        fecha_creacion = new Date();
    }
}
