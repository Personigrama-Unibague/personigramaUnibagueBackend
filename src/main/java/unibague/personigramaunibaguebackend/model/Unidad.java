package unibague.personigramaunibaguebackend.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="unidades")
public class Unidad {

    @Id
    @Getter @Setter private String id;

    @Getter @Setter private String nombre;

    @Getter @Setter private String parent_id;

}
