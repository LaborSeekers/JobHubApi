package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thelaborseekers.jobhubapi.model.entity.Postulacion;

@Repository
public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {
}
