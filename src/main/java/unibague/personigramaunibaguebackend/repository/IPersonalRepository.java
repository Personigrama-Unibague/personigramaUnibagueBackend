package unibague.personigramaunibaguebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unibague.personigramaunibaguebackend.model.Personal;

import java.util.List;

@Repository
public interface IPersonalRepository extends JpaRepository<Personal, Long> {

    @Query(value = "select * from personal where unidad = :und", nativeQuery = true)
    List<Personal> findByUnidad(String und);

    @Query(value = "select * from personal where cedula = :id", nativeQuery = true)
    Personal findByCedula(String id);

    @Query(value = "delete from personal where cedula = :id", nativeQuery = true)
    void deleteByCedula(String id);

}
