package thelaborseekers.jobhubapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thelaborseekers.jobhubapi.model.entity.User;

import java.util.List;

public interface AdminUserService {
    List<User> findAll();
    Page<User> paginate(Pageable pageable);
    User create(User user);
    User findById(Integer id);
    User update(Integer id,User updatedUser);
    void delete(Integer id);
}
