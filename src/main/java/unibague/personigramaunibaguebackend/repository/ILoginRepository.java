package unibague.personigramaunibaguebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unibague.personigramaunibaguebackend.model.Personal;

import java.util.List;

@Repository
public interface ILoginRepository extends JpaRepository<Personal, Long> {

    @Query(value = "SELECT (COUNT(*) > 0) AS existe FROM login WHERE usuario=:user and contraseña=:password", nativeQuery = true)
   Boolean loginAuthentication(String user, String password);
}
