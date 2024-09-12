package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thelaborseekers.jobhubapi.model.entity.Postulante;

import java.util.List;

public interface PostulanteRepository extends JpaRepository<Postulante, Integer> {
    @Query("SELECT COUNT(u)>0 FROM Postulante u WHERE u.email =:email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) > 0 FROM Postulante u WHERE u.email = :email AND u.id != :id")
    boolean existsBySlugAndIdNot(@Param("email") String email, @Param("id") Integer idNot);

    // Nuevo: Filtrar por nombre y apellido
    @Query("SELECT p FROM Postulante p WHERE LOWER(p.name) = LOWER(:name) AND LOWER(p.lastName) = LOWER(:lastName)")
    List<Postulante> findByNameAndLastName(@Param("name") String name, @Param("lastName") String lastName);

    // Nuevo: Filtrar por edad
    @Query("SELECT p FROM Postulante p WHERE YEAR(CURRENT_DATE) - YEAR(p.birthday) = :age")
    List<Postulante> findByAge(@Param("age") int age);
}
