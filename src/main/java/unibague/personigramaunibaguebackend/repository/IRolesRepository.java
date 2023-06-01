package unibague.personigramaunibaguebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.model.Roles;

import java.util.List;

//Interfaz que implementa JpaRepository y metodos query personalizados

@Repository
public interface IRolesRepository extends JpaRepository<Roles, Long> {

    /**
     * Query para guardar un nuevo rol
     *
     * @param id_jerar Nuevo id_jerarquico
     * @param nombre   Nombre del rol
     * @param unidad   Unidad a la que pertenece el rol
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO public.roles(id_jerar, nombre, unidad)VALUES(:id_jerar, :nombre, :unidad)", nativeQuery = true)
    void saveRol(Integer id_jerar, String nombre, String unidad);

    /**
     * Query para traer todos los roles por unidad
     *
     * @param unidad unidad a buscar
     * @return Lista de roles
     */
    @Transactional
    @Query(value = "select * from roles where unidad = :unidad or unidad='0'", nativeQuery = true)
    List<Roles> getAllRolesByUnidad(String unidad);

    /**
     * Query para borrar rol por id
     *
     * @param id id del rol
     */
    @Transactional
    @Modifying
    @Query(value = "delete from roles where id = :id", nativeQuery = true)
    void deleteRolById(Integer id);

    /**
     * Query para actualizar el nombre de un rol por id
     *
     * @param id     id del rol
     * @param nombre nuevo nombre del rol
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE roles SET nombre = :nombre WHERE id = :id", nativeQuery = true)
    void updateNameById(Integer id, String nombre);

    /**
     * Query para actualizar los Id_jerar de manera consecutiva
     * @param unidad Unidad a actualizar
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE roles SET id_jerar = new_id_jerar\n" +
            "FROM (SELECT id, ROW_NUMBER() OVER (ORDER BY id) AS new_id_jerar\n" +
            "\tFROM roles where unidad = :unidad\n" +
            ") AS sub WHERE roles.id = sub.id and unidad=:unidad", nativeQuery = true)
    void updateConsecutiveIdJerar(String unidad);


}
