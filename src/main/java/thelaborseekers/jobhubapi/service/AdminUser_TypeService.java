package thelaborseekers.jobhubapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thelaborseekers.jobhubapi.model.entity.Type_user;

import java.util.List;

public interface AdminUser_TypeService {
    List<Type_user> findAll();
    Page<Type_user> paginate(Pageable pageable);
    Type_user create(Type_user TypeU);
    Type_user findById(Integer id);
    Type_user update(Integer id,Type_user updatedTypeU);
    void delete(Integer id);
}
