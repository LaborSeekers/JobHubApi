package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelaborseekers.jobhubapi.model.entity.Type_user;

public interface TypeURepository extends JpaRepository<Type_user,Integer> {
}
