package unibague.personigramaunibaguebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import unibague.personigramaunibaguebackend.model.Personal;

import java.util.Date;
import java.util.List;

@Repository
public interface ILoginRepository extends JpaRepository<Personal, Long> {

    @Query(value = "SELECT (COUNT(*) > 0) AS existe FROM login WHERE usuario=:user and contraseña=:password", nativeQuery = true)
    Boolean loginAuthentication(String user, String password);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO public.login(contraseña, fecha_creacion, usuario)VALUES(:password, :fecha , :user)", nativeQuery = true)
    void saveNewUser(String password, Date fecha, String user);
}
