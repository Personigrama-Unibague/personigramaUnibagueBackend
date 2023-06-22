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
public interface IUnidadesRepository extends JpaRepository<Unidad, Long> {

    /**
     * Query para traer todas las unidades
     *
     * @return Lista de unidades
     */
    @Transactional
    @Modifying
    @Query(value = "select * from unidades order by id asc", nativeQuery = true)
    List<Unidad> getAllUnidades();

    /**
     * Query para traer el nombre por id
     *
     * @param id de la unidad
     * @return Nombre de la unidad
     */
    @Query(value = "select nombre from unidades where id = :id", nativeQuery = true)
    String getNameById(String id);

    /**
     * Query para traer una unidad por id
     *
     * @param id id de la unidad
     * @return Unidad
     */
    @Query(value = "select * from unidades where id = :id", nativeQuery = true)
    Unidad getUndById(String id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM public.unidades WHERE id=:id", nativeQuery = true)
    void deleteUnidadById(String id);
}
