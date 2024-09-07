package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.model.entity.Postulante;
import thelaborseekers.jobhubapi.repository.UserRepository;
import thelaborseekers.jobhubapi.service.AdminUserService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Postulante> findAll() {return userRepository.findAll();}


    @Transactional(readOnly = true)
    @Override
    public Page<Postulante> paginate(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Postulante create(Postulante postulante) {
        return userRepository.save(postulante);
    }

    @Transactional(readOnly = true)
    @Override
    public Postulante findById(Integer id) {
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found with id: " + id));
    }

    @Transactional
    @Override
    public Postulante update(Integer id, Postulante updatedPostulante) {
        Postulante postulanteFromDb = findById(id);

        postulanteFromDb.setName(updatedPostulante.getName());
        postulanteFromDb.setLastName(updatedPostulante.getLastName());
        postulanteFromDb.setEmail(updatedPostulante.getEmail());
        postulanteFromDb.setPhone(updatedPostulante.getPhone());
        postulanteFromDb.setBirthday(updatedPostulante.getBirthday());
        postulanteFromDb.setPassword(updatedPostulante.getPassword());

        return userRepository.save(postulanteFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Postulante postulante = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found with id: " + id));
        userRepository.delete(postulante);
    }
}
