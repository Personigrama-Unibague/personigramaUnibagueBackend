package unibague.personigramaunibaguebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.model.Unidad;

import java.util.List;

@Repository
public interface IPersonalRepository extends JpaRepository<Personal, Long> {

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

    @Query(value = "select * from personal where unidad = :und", nativeQuery = true)
    List<Personal> findByUnidad(String und);

    @Query(value = "select * from personal where cedula=:id limit 1", nativeQuery = true)
    Personal findByCedula(String id);

    @Query(value = "select (COUNT(*) > 0) AS existe from personal where cedula = :id", nativeQuery = true)
    Boolean existByCedula(String id);

    @Transactional
    @Modifying
    @Query(value = "delete from personal where cedula = :id and unidad = :unidad", nativeQuery = true)
    void deleteByCedula(String id, String unidad);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO personal\n" +
            "(nombre, cargo, cedula, correo, \"extension\", foto, telefono, unidad, id_jerar)\n" +
            "VALUES(:nombre, :cargo, :cedula, :correo, :extension, :foto, :telefono, :unidad, 0);", nativeQuery = true)
    void savePerson(String cedula, String cargo, String correo, Integer extension, String foto, String nombre, String telefono, String unidad);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO public.personal\n" +
            "(cedula, cargo, correo, \"extension\", foto, id_jerar, nombre, telefono, unidad_id)\n" +
            "VALUES(:cedula, :cargo, :correo, :extension, :foto, 0, :nombre, :telefono, :und );", nativeQuery = true)
    void guardarJson(String cedula, String cargo, String correo, Integer extension, String foto, String nombre, String telefono, Unidad und);

}
