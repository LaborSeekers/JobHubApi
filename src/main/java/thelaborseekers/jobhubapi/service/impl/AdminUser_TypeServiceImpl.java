package thelaborseekers.jobhubapi.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.model.entity.Type_user;
import thelaborseekers.jobhubapi.repository.TypeURepository;
import thelaborseekers.jobhubapi.service.AdminUser_TypeService;


import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminUser_TypeServiceImpl implements AdminUser_TypeService {
    private final TypeURepository TypeUserRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Type_user> findAll() {return TypeUserRepository.findAll();}

    @Transactional(readOnly = true)
    @Override
    public Page<Type_user> paginate(Pageable pageable) {
        return TypeUserRepository.findAll(pageable);
    }


    @Transactional
    @Override
    public Type_user create(Type_user TypeU) {
        return TypeUserRepository.save(TypeU);
    }


    @Transactional(readOnly = true)
    @Override
    public Type_user findById(Integer id) {
        return TypeUserRepository.findById(id).orElseThrow(()->new RuntimeException("Type_User not found by ID"));
    }


    @Transactional
    @Override
    public Type_user update(Integer id, Type_user updatedTypeU) {
        Type_user TypeUFromDb = findById(id);
        TypeUFromDb.setName(updatedTypeU.getName());
        TypeUFromDb.setDescription(updatedTypeU.getDescription());
        return TypeUserRepository.save(TypeUFromDb);
    }


    @Transactional
    @Override
    public void delete(Integer id) {
        Type_user TypeU = findById(id);
        TypeUserRepository.delete(TypeU);
    }
}
