package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelaborseekers.jobhubapi.model.entity.Ofertante;

import java.util.Optional;

public interface OfertanteRepository extends JpaRepository<Ofertante, Integer> {

    Optional<Ofertante> findByNameAndLastName(String name, String lastName);

    boolean existsByNameAndLastName(String name, String lastName);
    //Metodo para verificar si ya existe un autor con el mismo nombre y apellido, excepto el usuario actual
    boolean existsByNameAndLastNameAndUserIdNot(String name, String lastName, Integer userId);
}