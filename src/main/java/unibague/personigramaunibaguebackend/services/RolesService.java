package unibague.personigramaunibaguebackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unibague.personigramaunibaguebackend.model.Roles;
import unibague.personigramaunibaguebackend.repository.ILoginRepository;
import unibague.personigramaunibaguebackend.repository.IRolesRepository;

@Service
public class RolesService {

    /**
     * Repositorio
     */
    @Autowired
    private IRolesRepository iRolesRepository;

    public void agregarRol(Integer id_jerar, String nombre, String unidad ) {
        try {
             iRolesRepository.saveRol(id_jerar,nombre,unidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
