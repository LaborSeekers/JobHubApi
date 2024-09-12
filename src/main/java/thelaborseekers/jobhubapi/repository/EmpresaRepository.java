package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelaborseekers.jobhubapi.model.entity.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa,Integer> {
}
