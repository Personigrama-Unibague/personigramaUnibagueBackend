package unibague.personigramaunibaguebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.model.Unidad;

import java.util.List;

@Repository
public interface IUnidadesRepository extends JpaRepository<Unidad, Long> {

    @Query(value = "select nombre from unidades where id = :id", nativeQuery = true)
    String getNameById(String id);
}
