package unibague.personigramaunibaguebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.model.Unidad;

import java.util.List;

@Repository
public interface IPersonalRepository extends JpaRepository<Personal, Long> {

    @Query(value = "select * from personal where unidad = :und", nativeQuery = true)
    List<Personal> findByUnidad(String und);

    @Query(value = "select * from personal where cedula = :id", nativeQuery = true)
    Personal findByCedula(String id);

    @Query(value = "select (COUNT(*) > 0) AS existe from personal where cedula = :id", nativeQuery = true)
    Boolean existByCedula(String id);

    @Query(value = "delete from personal where cedula = :id", nativeQuery = true)
    void deleteByCedula(String id);

    @Query(value = "INSERT INTO public.personal\n" +
            "(cedula, cargo, correo, \"extension\", foto, id_jerar, nombre, telefono, unidad_id)\n" +
            "VALUES(:cedula, :cargo, :correo, :extension, :foto, 0, :nombre, :telefono, :und );", nativeQuery = true)
    void guardarJson(String cedula, String cargo, String correo, Integer extension, String foto, String nombre, String telefono, Unidad und);

}
