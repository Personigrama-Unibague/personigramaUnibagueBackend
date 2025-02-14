package unibague.personigramaunibaguebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import unibague.personigramaunibaguebackend.model.Roles;

import java.util.List;

//Interfaz que implementa JpaRepository y metodos query personalizados

@Repository
public interface IRolesRepository extends JpaRepository<Roles, Long> {

    /**
     * Query para guardar un nuevo rol
     *
     * @param nombre Nombre del rol
     * @param unidad Unidad a la que pertenece el rol
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO roles (id_jerar, nombre, unidad) VALUES (" +
            "(SELECT COALESCE(MAX(id_jerar), 1) + 1 FROM roles WHERE unidad = :unidad), " +
            ":nombre, :unidad)", nativeQuery = true)
    void saveRol(String nombre, String unidad);

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
     *
     * @param unidad Unidad a actualizar
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE roles\n" +
            "SET id_jerar = subquery.new_id\n" +
            "FROM (\n" +
            "    SELECT id, ROW_NUMBER() OVER (ORDER BY id) + 1 AS new_id\n" +
            "    FROM roles where unidad='4'\n" +
            ") AS subquery\n" +
            "WHERE roles.id = subquery.id and unidad=:unidad", nativeQuery = true)
    void updateConsecutiveIdJerar(String unidad);

    /**
     * Query para cambiar el id_jerar de un rol y actualizar el resto
     *
     * @param antiguo_id_jerar antiguo id_jerar del rol
     * @param nuevo_id_jerar   nuevo id_jerar del rol
     * @param unidad           unidad del rol
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE roles " +
            "SET id_jerar = CASE " +
            "  WHEN id_jerar = ?1 THEN ?2 " +
            "  WHEN ?2 < ?1 AND id_jerar >= ?2 AND id_jerar <= ?1 THEN id_jerar + 1 " +
            "  WHEN ?2 > ?1 AND id_jerar >= ?1 AND id_jerar <= ?2 THEN id_jerar - 1 " +
            "  ELSE id_jerar " +
            "END " +
            "WHERE unidad = ?3 " +
            "  AND ((?2 < ?1 AND id_jerar >= ?2 AND id_jerar <= ?1) " +
            "       OR (?2 > ?1 AND id_jerar >= ?1 AND id_jerar <= ?2))", nativeQuery = true)
    void updateIdJerarRol(Integer antiguo_id_jerar, Integer nuevo_id_jerar, String unidad);

    /**
     * Query para cambiar el id_jerar de una persona y actualizar el resto
     *
     * @param antiguo_id_jerar antiguo id_jerar del rol
     * @param nuevo_id_jerar   nuevo id_jerar del rol
     * @param unidad           unidad del rol
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE personal " +
            "SET id_jerar = updated_roles.new_id_jerar " +
            "FROM ( " +
            "  SELECT id, " +
            "         CASE " +
            "           WHEN id_jerar = ?1 THEN ?2 " +
            "           WHEN ?2 < ?1 AND id_jerar >= ?2 AND id_jerar <= ?1 THEN id_jerar + 1 " +
            "           WHEN ?2 > ?1 AND id_jerar >= ?1 AND id_jerar <= ?2 THEN id_jerar - 1 " +
            "           ELSE id_jerar " +
            "         END AS new_id_jerar " +
            "  FROM personal " +
            "  WHERE unidad = ?3 " +
            "    AND ((?2 < ?1 AND id_jerar >= ?2 AND id_jerar <= ?1) " +
            "         OR (?2 > ?1 AND id_jerar >= ?1 AND id_jerar <= ?2)) " +
            ") AS updated_roles " +
            "WHERE personal.id = updated_roles.id", nativeQuery = true)
    void updatePersonasWithNewOrder(Integer antiguo_id_jerar, Integer nuevo_id_jerar, String unidad);


    /**
     * Metodo para eliminar roles por unidad
     *
     * @param unidad unidad a la que pertenece el rol
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM roles WHERE unidad=:unidad", nativeQuery = true)
    void deleteRolByUnidad(String unidad);

    /**
     * Metodo para contar los roles por unidad
     *
     * @param unidad unidad a la que pertenece el rol
     * @return Numero de roles en la unidad
     */
    @Query(value = "select count(*) from roles WHERE unidad=:unidad", nativeQuery = true)
    Integer countRolByUnidad(String unidad);
}
