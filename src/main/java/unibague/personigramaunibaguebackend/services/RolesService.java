package unibague.personigramaunibaguebackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unibague.personigramaunibaguebackend.model.Roles;
import unibague.personigramaunibaguebackend.repository.IRolesRepository;

import java.util.List;

//Servicio que contiene los metodos para administrar los roles

@Service
public class RolesService {

    /**
     * Repositorio
     */
    @Autowired
    private IRolesRepository iRolesRepository;


    /**
     * Controlador para guardar roles
     *
     * @param nombre Nombre del rol
     * @param unidad Unidad a la que va a pertenecer el rol
     */
    public void getSaveRol(String nombre, String unidad) {
        try {
            iRolesRepository.saveRol(nombre, unidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Controlador para traer los roles por unidad
     *
     * @param unidad unidad a la que pertenecen los roles
     * @return Lista de roles
     */
    public List<Roles> getAllRolesByUnidad(String unidad) {
        try {
            List<Roles> roles = iRolesRepository.getAllRolesByUnidad(unidad);
            return roles;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Controlador para borrar un rol por id
     *
     * @param id Id del rol
     */
    public void getDeleteRolById(Integer id, String unidad) {
        try {
            iRolesRepository.deleteRolById(id);
            iRolesRepository.updateConsecutiveIdJerar(unidad);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Controlador para actualizar el nombre de un rol
     *
     * @param id     id del rol
     * @param nombre nuevo nombre del rol
     */
    public void getUpdateNameById(Integer id, String nombre) {
        try {
            iRolesRepository.updateNameById(id, nombre);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para cambiar el id_jerar de un rol y actualizar el resto
     *
     * @param antiguo antiguo id_jerar del rol
     * @param nuevo   nuevo id_jerar del rol
     * @param unidad  unidad del rol
     */
    @Transactional
    public void getUpdateIdJerarRol(Integer antiguo, Integer nuevo, String unidad) {
        try {
            // Primero, actualizamos los roles para reflejar el nuevo orden.
            iRolesRepository.updateIdJerarRol(antiguo, nuevo, unidad);

            // Luego, actualizamos las personas para asociarlas correctamente con los roles reorganizados.
            iRolesRepository.updatePersonasWithNewOrder(antiguo, nuevo, unidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
