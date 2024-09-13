package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thelaborseekers.jobhubapi.model.entity.Postulante;

public interface PostulanteRepository extends JpaRepository<Postulante, Integer> {
    @Query("SELECT COUNT(u)>0 FROM Postulante u WHERE u.email =:email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT p FROM Postulante p WHERE p.email = :email")
    Postulante findByEmail(@Param("email") String email);

    @Query("SELECT p FROM Postulante p WHERE p.recoveryToken = :token")
    Postulante findByRecoveryToken(@Param("token") String token);

    @Query("SELECT COUNT(u) > 0 FROM Postulante u WHERE u.email = :email AND u.id != :id")
    boolean existsBySlugAndIdNot(@Param("email") String email, @Param("id") Integer idNot);
}
