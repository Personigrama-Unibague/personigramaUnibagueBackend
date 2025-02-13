package unibague.personigramaunibaguebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.model.Unidad;

import java.util.List;

// Interfaz que implementa JpaRepository y métodos query personalizados

@Repository
public interface IPersonalRepository extends JpaRepository<Personal, Long> {

    /**
     * Query que trae el personal de la universidad
     *
     * @return Lista de personas
     */
    @Query(value = "SELECT * FROM personal", nativeQuery = true)
    List<Personal> getAllPersonal();

    /**
     * Query que trae el personal de la universidad por unidad de forma distinct
     *
     * @param unidad unidad
     * @return Lista de personas
     */
    @Query(value = "SELECT * FROM ("
            + "SELECT DISTINCT ON (cedula) * FROM personal "
            + "WHERE cedula NOT IN (SELECT cedula FROM personal WHERE unidad = :unidad)) AS consulta "
            + "ORDER BY nombre ASC", nativeQuery = true)
    List<Personal> getAllDistinct(String unidad);

    /**
     * Query para encontrar personas por unidad
     *
     * @param und unidad
     * @return Lista de personas
     */
    @Query(value = "SELECT * FROM personal WHERE unidad = :und", nativeQuery = true)
    List<Personal> findByUnidad(String und);

    /**
     * Query para encontrar personas por cédula
     *
     * @param id cédula
     * @return Persona
     */
    @Query(value = "SELECT * FROM personal WHERE cedula = :id LIMIT 1", nativeQuery = true)
    Personal findByCedula(String id);

    /**
     * Query para validar si la persona existe por cédula
     *
     * @param id cédula de la persona
     * @return true o false
     */
    @Query(value = "SELECT (COUNT(*) > 0) AS existe FROM personal WHERE cedula = :id", nativeQuery = true)
    Boolean existByCedula(String id);

    /**
     * Query para eliminar una persona por cédula y unidad
     *
     * @param id     ID de la persona
     * @param unidad unidad a la que pertenece la persona
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM personal WHERE cedula = :id AND unidad = :unidad", nativeQuery = true)
    void deleteByCedulaAndUnidad(String id, String unidad);

    /**
     * Query para eliminar una persona por cédula
     *
     * @param cedula cédula de la persona
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM personal WHERE cedula = :cedula", nativeQuery = true)
    void deleteByCedula(String cedula);

    /**
     * Query para eliminar una persona por unidad
     *
     * @param unidad Unidad a la que pertenece la persona
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM personal WHERE unidad = :unidad", nativeQuery = true)
    void deleteByUnidad(String unidad);

    /**
     * Query para contar personas por unidad
     *
     * @param unidad Unidad a la que pertenece la persona
     * @return Número de personas en la unidad
     */
    @Query(value = "SELECT COUNT(*) FROM personal WHERE unidad = :unidad", nativeQuery = true)
    Integer countPersonalByUnidad(String unidad);

    /**
     * Query para guardar una persona
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO personal (nombre, cargo, cedula, correo, extension, foto, telefono, unidad, id_jerar) "
            + "VALUES (:nombre, :cargo, :cedula, :correo, :extension, :foto, :telefono, :unidad, 0)", nativeQuery = true)
    void savePerson(String cedula, String cargo, String correo, Integer extension, String foto, String nombre, String telefono, String unidad);

    /**
     * Query para actualizar el id_jerarquico de la persona por cédula y unidad
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE personal SET id_jerar = :id_jerar WHERE cedula = :cedula AND unidad = :unidad", nativeQuery = true)
    void updateIdJerarByCedulaUnd(Integer id_jerar, String cedula, String unidad);

    /**
     * Query para actualizar el id_jerarquico a default
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE personal SET id_jerar = 0 WHERE cedula = :cedula AND unidad = :unidad", nativeQuery = true)
    void updateIdJerarDefault(String cedula, String unidad);

    /**
     * Query para actualizar todas las personas de la unidad el Id_jerar a default
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE personal SET id_jerar = 0 WHERE unidad = :unidad AND id_jerar = :id_jerar", nativeQuery = true)
    void updateIdJerarDefaultAllSection(String unidad, Integer id_jerar);

    /**
     * Query para actualizar masivamente el Id_jerar de todas las personas de la unidad
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE personal SET id_jerar = :new_id_jerar WHERE unidad = :unidad AND id_jerar = :old_id_jerar", nativeQuery = true)
    void updateIdJerarNewUpdatedRol(Integer old_id_jerar, Integer new_id_jerar, String unidad);

    /**
     * Query para actualizar cargo, extensión, foto, correo y teléfono de una persona por cédula
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE personal SET "
            + "cargo = :cargo, "
            + "extension = :extension, "
            + "foto = :foto, "
            + "correo = :correo, "
            + "telefono = :telefono "
            + "WHERE cedula = :cedula", nativeQuery = true)
    void updateMDWChangingValues(String cargo, Integer extension, String foto, String correo, String telefono, String cedula);

    /**
     * Query para actualizar la unidad de una persona si es "ORIGINAL"
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE personal SET unidad = :unidad, telefono = :telefono WHERE cedula = :cedula AND original = 'ORIGINAL'", nativeQuery = true)
    void updateOriginalUnidadMDW(String unidad, String telefono, String cedula);
    

    /**
     * Query para insertar información de JSON a la base de datos
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO personal (cedula, cargo, correo, extension, foto, id_jerar, nombre, telefono, unidad) "
            + "VALUES (:cedula, :cargo, :correo, :extension, :foto, 0, :nombre, :telefono, :unidad)", nativeQuery = true)
    void guardarJson(String cedula, String cargo, String correo, Integer extension, String foto, String nombre, String telefono, String unidad);
}


