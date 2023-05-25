package unibague.personigramaunibaguebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import unibague.personigramaunibaguebackend.model.Personal;
import unibague.personigramaunibaguebackend.model.Roles;

@Repository
public interface IRolesRepository extends JpaRepository<Roles, Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO public.roles(id_jerar, nombre, unidad)VALUES(:id_jerar, :nombre, :unidad)", nativeQuery = true)
    void saveRol(Integer id_jerar, String nombre, String unidad);

    @Transactional
    @Query(value = "select * from roles where id_jerar = :id_jerar and unidad = :unidad';", nativeQuery = true)
    void findRolBy(Integer id_jerar, String unidad);
}
