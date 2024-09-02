package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thelaborseekers.jobhubapi.model.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT COUNT(u)>0 FROM User u WHERE u.email =:email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email AND u.id != :id")
    boolean existsBySlugAndIdNot(@Param("email") String email, @Param("id") Integer idNot);
}
