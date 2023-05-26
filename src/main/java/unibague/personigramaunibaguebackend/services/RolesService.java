package unibague.personigramaunibaguebackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unibague.personigramaunibaguebackend.model.Roles;
import unibague.personigramaunibaguebackend.repository.ILoginRepository;
import unibague.personigramaunibaguebackend.repository.IRolesRepository;

import java.util.List;

@Service
public class RolesService {

    /**
     * Repositorio
     */
    @Autowired
    private IRolesRepository iRolesRepository;

    public void getAgregarRol(Integer id_jerar, String nombre, String unidad ) {
        try {
             iRolesRepository.saveRol(id_jerar,nombre,unidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Roles> getAllRolesByUnidad(String unidad ) {
        try {
            List<Roles> roles = iRolesRepository.getAllRolesByUnidad(unidad);
            return roles;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
