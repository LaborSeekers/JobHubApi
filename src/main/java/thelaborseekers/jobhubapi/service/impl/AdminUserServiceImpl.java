package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.model.entity.User;
import thelaborseekers.jobhubapi.repository.UserRepository;
import thelaborseekers.jobhubapi.service.AdminUserService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {return userRepository.findAll();}


    @Transactional(readOnly = true)
    @Override
    public Page<User> paginate(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found with id: " + id));
    }

    @Transactional
    @Override
    public User update(Integer id, User updatedUser) {
        User userFromDb = findById(id);

        userFromDb.setName(updatedUser.getName());
        userFromDb.setLastName(updatedUser.getLastName());
        userFromDb.setEmail(updatedUser.getEmail());
        userFromDb.setPhone(updatedUser.getPhone());
        userFromDb.setBirthday(updatedUser.getBirthday());
        userFromDb.setPassword(updatedUser.getPassword());
        userFromDb.setType_user(updatedUser.getType_user());

        return userRepository.save(userFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }
}
