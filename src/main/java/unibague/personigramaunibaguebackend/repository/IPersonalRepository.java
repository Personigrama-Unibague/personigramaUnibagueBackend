package unibague.personigramaunibaguebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.model.Unidad;

import java.util.List;

//Interfaz que implementa JpaRepository y metodos query personalizados

@Repository
public interface IPersonalRepository extends JpaRepository<Personal, Long> {

    /**
     * Query que trae el personal de la universidad
     *
     * @return Lista de personas
     */
    @Query(value = "select * from personal", nativeQuery = true)
    List<Personal> getAllPersonal();

    /**
     * Query que trae el personal de la universidad por unidad de forma distinct
     *
     * @param unidad unidad
     * @return Lista de personas
     */
    @Query(value = "select * \n" +
            "from(\n" +
            "\tSELECT DISTINCT on (cedula) *\n" +
            "\tFROM personal\n" +
            "\tWHERE cedula NOT IN (\n" +
            "\t    SELECT cedula\n" +
            "\t    FROM personal\n" +
            "\t    WHERE unidad = :unidad\n" +
            ") \n" +
            ")as consulta\n" +
            "order by nombre asc;", nativeQuery = true)
    List<Personal> getAllDistinct(String unidad);

    /**
     * Query para encontrar personas por unidad
     *
     * @param und unidad
     * @return Lista de personas
     */
    @Query(value = "select * from personal where unidad = :und", nativeQuery = true)
    List<Personal> findByUnidad(String und);

    /**
     * Query para encontrar personas por cedula
     *
     * @param id cedula
     * @return Persona
     */
    @Query(value = "select * from personal where cedula=:id limit 1", nativeQuery = true)
    Personal findByCedula(String id);

    /**
     * Query para validar si la personas existe por cedula
     *
     * @param id cedula de la persona
     * @return true or false
     */
    @Query(value = "select (COUNT(*) > 0) AS existe from personal where cedula = :id", nativeQuery = true)
    Boolean existByCedula(String id);

    /**
     * Query para eliminar una persona por cedula y unidad
     *
     * @param id     id de la persona
     * @param unidad unidad a la que pertenece la persona
     */
    @Transactional
    @Modifying
    @Query(value = "delete from personal where cedula = :id and unidad = :unidad", nativeQuery = true)
    void deleteByCedulaAndUnidad(String id, String unidad);

    /**
     * Query para eliminar una persona por cedula
     *
     * @param cedula cedula de la persona
     */
    @Transactional
    @Modifying
    @Query(value = "delete from personal where cedula = :cedula", nativeQuery = true)
    void deleteByCedula(String cedula);

    /**
     * Query para eliminar una persona por unidad
     *
     * @param unidad
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM personal WHERE unidad=:unidad", nativeQuery = true)
    void deleteByUnidad(String unidad);


    /**
     * Metodo para contar personas por unidad
     *
     * @param unidad Unidad a la que pertenece la persona
     * @return Numero de personas en la unidad
     */
    @Query(value = "select count(*) from personal where unidad = :unidad", nativeQuery = true)
    Integer countPersonalByUnidad(String unidad);

    /**
     * Query para guardar persona
     *
     * @param cedula    cedula de la persona
     * @param cargo     cargo de la persona
     * @param correo    correo de la persona
     * @param extension extension de la persona
     * @param foto      foto de la persona
     * @param nombre    nombre de la persona
     * @param telefono  telefono de la persona
     * @param unidad    unidad de la persona
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO personal\n" +
            "(nombre, cargo, cedula, correo, \"extension\", foto, telefono, unidad, id_jerar)\n" +
            "VALUES(:nombre, :cargo, :cedula, :correo, :extension, :foto, :telefono, :unidad, 0);", nativeQuery = true)
    void savePerson(String cedula, String cargo, String correo, Integer extension, String foto, String nombre, String telefono, String unidad);

    /**
     * Query para actualizar el id_jerarquico de la persona por cedula y unidad
     *
     * @param id_jerar nuevo id_jerar
     * @param cedula   cedula de la persona
     * @param unidad   unidad de la persona
     */
    @Transactional
    @Modifying
    @Query(value = "update personal set id_jerar=:id_jerar where cedula=:cedula and unidad=:unidad", nativeQuery = true)
    void updateIdJerarByCedulaUnd(Integer id_jerar, String cedula, String unidad);

    /**
     * Query para actualizar el id_jerarquico a default
     *
     * @param cedula cedula de la persona
     * @param unidad unidad a la que pertenece
     */
    @Transactional
    @Modifying
    @Query(value = "update personal set id_jerar=0 where cedula=:cedula and unidad=:unidad", nativeQuery = true)
    void updateIdJerarDefault(String cedula, String unidad);

    /**
     * Query para actualizar masivamente a todas las personas de la unidad el Id_jerar a default
     *
     * @param unidad   unidad a modificar
     * @param id_jerar id_jerar a modificar
     */
    @Transactional
    @Modifying
    @Query(value = "update personal set id_jerar=0 where unidad=:unidad and id_jerar=:id_jerar", nativeQuery = true)
    void updateIdJerarDefaultAllSection(String unidad, Integer id_jerar);

    /**
     * Query para actualizar masivamente a todas las personas de la unidad el Id_jerar a default
     *
     * @param unidad       unidad a modificar
     * @param old_id_jerar viejo id_jerar a modificar
     * @param new_id_jerar nuevo id_jerar a modificar
     */
    @Transactional
    @Modifying
    @Query(value = "update personal set id_jerar=:new_id_jerar where unidad=:unidad and id_jerar=:old_id_jerar", nativeQuery = true)
    void updateIdJerarNewUpdatedRol(Integer old_id_jerar, Integer new_id_jerar, String unidad);

    @Transactional
    @Modifying
    @Query(value = "update personal set \n" +
            "cargo=:cargo,\n" +
            "extension=:extension,\n" +
            "foto=:foto,\n" +
            "correo=:correo\n" +
            "where cedula=:cedula ", nativeQuery = true)
    void updateMDWChangingValues(String cargo, Integer extension, String foto, String correo, String cedula);

    @Transactional
    @Modifying
    @Query(value = "update personal set unidad=:unidad where cedula=:cedula and original='ORIGINAL'", nativeQuery = true)
    void updateOriginalUnidadMDW(String unidad, String cedula);


    //----------------------------------------------------------------------------------------------------------

    /**
     * Query para pasar informacion de Json a Base De Datos
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO public.personal\n" +
            "(cedula, cargo, correo, \"extension\", foto, id_jerar, nombre, telefono, unidad_id)\n" +
            "VALUES(:cedula, :cargo, :correo, :extension, :foto, 0, :nombre, :telefono, :und );", nativeQuery = true)
    void guardarJson(String cedula, String cargo, String correo, Integer extension, String foto, String nombre, String telefono, Unidad und);

}
