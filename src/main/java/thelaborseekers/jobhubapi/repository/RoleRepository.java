package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelaborseekers.jobhubapi.model.entity.Role;
import thelaborseekers.jobhubapi.model.enums.Erole;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    //Buscar un rol por nombre
    Optional<Role> findByName(Erole name);

}
