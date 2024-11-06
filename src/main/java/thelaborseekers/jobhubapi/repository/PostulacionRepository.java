package thelaborseekers.jobhubapi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.entity.Postulacion;

@Repository
public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {
     // Nuevo método para obtener el historial de postulaciones de un postulante
    List<Postulacion> findByPostulanteId(Integer postulanteId);
    // Método que encuentra postulaciones según una oferta laboral
    List<Postulacion> findByOfertaLaboral(JobOffer jobOffer);
    List<Postulacion> findByOfertaLaboralId(Long ofertaLaboralId);
}
