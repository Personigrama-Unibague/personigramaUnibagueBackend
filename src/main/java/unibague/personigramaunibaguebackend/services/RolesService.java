package unibague.personigramaunibaguebackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unibague.personigramaunibaguebackend.model.Roles;
import unibague.personigramaunibaguebackend.repository.ILoginRepository;
import unibague.personigramaunibaguebackend.repository.IPersonalRepository;
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

    @Autowired
    private IPersonalRepository iPersonalRepository;

    /**
     * Controlador para guardar roles
     *
     * @param id_jerar Id_jerar de los roles
     * @param nombre   Nombre del rol
     * @param unidad   Unidad a la que va a pertenecer el rol
     */
    public void getSaveRol(Integer id_jerar, String nombre, String unidad) {
        try {
            iRolesRepository.saveRol(id_jerar, nombre, unidad);
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
    public void getUpdateIdJerarRol(Integer antiguo, Integer nuevo, String unidad) {
        try {
            iRolesRepository.updateIdJerarRol(antiguo, nuevo, unidad);
            iPersonalRepository.updateIdJerarNewUpdatedRol(antiguo, nuevo, unidad);
            System.out.println("------------- pruebas --------------------");
            System.out.println(antiguo);
            System.out.println(nuevo);
            System.out.println(unidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
