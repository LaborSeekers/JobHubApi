package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thelaborseekers.jobhubapi.model.entity.Ofertante;

public interface OfertanteRepository extends JpaRepository<Ofertante, Integer> {

    @Query("SELECT COUNT(o)>0 FROM Ofertante o WHERE o.email =:email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT COUNT(o) > 0 FROM Ofertante o WHERE o.email = :email AND o.id != :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Integer idNot);
}
