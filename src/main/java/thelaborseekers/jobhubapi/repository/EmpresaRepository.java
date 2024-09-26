package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelaborseekers.jobhubapi.model.entity.Empresa;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa,Integer> {
    Optional<Empresa> findByName(String name);
}
