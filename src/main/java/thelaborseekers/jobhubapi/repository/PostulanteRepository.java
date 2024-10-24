package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thelaborseekers.jobhubapi.model.entity.Postulante;

import java.util.List;

public interface PostulanteRepository extends JpaRepository<Postulante, Integer> {
    //@Query("SELECT COUNT(u)>0 FROM Postulante u WHERE u.email =:email")
   // boolean existsByEmail(@Param("email") String email);
    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    boolean existsByFirstNameAndLastNameAndUserIdNot(String firstName, String lastName, Integer userId);
    //@Query("SELECT p FROM Postulante p WHERE p.email = :email")
    //Postulante findByEmail(@Param("email") String email);

    //Optional<Postulante> findByEmail(String email);

    // Nuevo: Filtrar por nombre y apellido
    @Query("SELECT p FROM Postulante p WHERE LOWER(p.firstName) = LOWER(:firstName) AND LOWER(p.lastName) LIKE LOWER(CONCAT(:lastName, '%'))")
    List<Postulante> findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    // Nuevo: Filtrar por edad
    @Query("SELECT p FROM Postulante p WHERE YEAR(CURRENT_DATE) - YEAR(p.birthday) = :age")
    List<Postulante> findByAge(@Param("age") int age);
}
