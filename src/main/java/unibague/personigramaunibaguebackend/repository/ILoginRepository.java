package unibague.personigramaunibaguebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import unibague.personigramaunibaguebackend.model.Login;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.model.ResumeLogin;

import java.util.Date;
import java.util.List;

//Interfaz que implementa JpaRepository y metodos query personalizados

@Repository
public interface ILoginRepository extends JpaRepository<Login, Long> {

    /**
     * Query que trae todos los usuarios para loggeo
     * @return Lista de usuarios
     */
    @Query(value = "select * from login", nativeQuery = true)
    List<Login> findAllUsers();

    /**
     * Query para autenticacion de Login
     * @param user Usuario
     * @param password Contraseña
     * @return true or false
     */
    @Query(value = "SELECT (COUNT(*) > 0) AS existe FROM login WHERE usuario=:user and contrasena=:password", nativeQuery = true)
    Boolean loginAuthentication(String user, String password);

    /**
     * Query para guardar un nuevo usuario
     * @param password contraseña
     * @param fecha fecha de creacion
     * @param user usuario
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO public.login(contrasena, fecha_creacion, usuario)VALUES(:password, :fecha , :user)", nativeQuery = true)
    void saveNewUser(String password, Date fecha, String user);

    /**
     * Query para eliminar usuarios por id
     * @param id id del usuario
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM login WHERE id=:id", nativeQuery = true)
    void deleteUserById(Integer id);
}
