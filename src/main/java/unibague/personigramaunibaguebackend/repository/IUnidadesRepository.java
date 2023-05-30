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
public interface IUnidadesRepository extends JpaRepository<Unidad, Long> {

    @Transactional
    @Modifying
    @Query(value = "select * from unidades order by id asc", nativeQuery = true)
    List<Unidad> getAllUnidades();

    @Query(value = "select nombre from unidades where id = :id", nativeQuery = true)
    String getNameById(String id);

    @Query(value = "select * from unidades where id = :id", nativeQuery = true)
    Unidad getUndById(String id);
}
