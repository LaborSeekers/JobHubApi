package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thelaborseekers.jobhubapi.model.entity.Entrevista;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EntrevistaRepository extends JpaRepository<Entrevista, Integer> {

    // Método para obtener entrevistas programadas por ID del ofertante
    List<Entrevista> findAllByOfertanteId(Integer ofertanteId);

    // Método para obtener entrevistas programadas por ID del postulante
    List<Entrevista> findAllByPostulanteId(Integer postulanteId);

    // Método para buscar una entrevista por fecha
    @Query("SELECT e FROM Entrevista e WHERE e.interviewDate = :interviewDate")
    Optional<Entrevista> findByInterviewDate(@Param("interviewDate") LocalDateTime interviewDate);

    Optional<Entrevista> findByPostulanteIdAndInterviewDate(Integer postulanteId, LocalDateTime interviewDate);

    Optional<Entrevista> findByOfertanteIdAndInterviewDate(Integer ofertanteId, LocalDateTime interviewDate);

}
