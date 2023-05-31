package unibague.personigramaunibaguebackend.model;

import lombok.*;

import javax.persistence.Id;

//Modelo que mapea el id y el usuario de los usuarios de base de datos

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResumeLogin {

    //Id generado en base de datos
    @Id
    @Getter
    @Setter
    private String id;

    //Usuario para loggeo
    @Getter
    @Setter
    private String usuario;
}
