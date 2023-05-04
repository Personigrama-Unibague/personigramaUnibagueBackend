package unibague.personigramaunibaguebackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Unidad {

    @Getter @Setter private String id;

    @Getter @Setter private String nombre;

    @Getter @Setter private String parent_id;

}
