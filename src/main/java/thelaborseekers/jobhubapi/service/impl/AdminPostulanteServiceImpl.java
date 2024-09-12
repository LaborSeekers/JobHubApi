package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.model.entity.Postulante;
import thelaborseekers.jobhubapi.repository.PostulanteRepository;
import thelaborseekers.jobhubapi.service.AdminPostulanteService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminPostulanteServiceImpl implements AdminPostulanteService {
    private final PostulanteRepository postulanteRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Postulante> findAll() {return postulanteRepository.findAll();}


    @Transactional(readOnly = true)
    @Override
    public Page<Postulante> paginate(Pageable pageable) {
        return postulanteRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Postulante create(Postulante postulante) {
        if(postulanteRepository.existsByEmail(postulante.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }
        return postulanteRepository.save(postulante);
    }

    @Transactional(readOnly = true)
    @Override
    public Postulante findById(Integer id) {
        return postulanteRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found with id: " + id));
    }

    @Transactional
    @Override
    public Postulante update(Integer id, Postulante updatedPostulante) {
        Postulante postulanteFromDb = findById(id);

        if (postulanteRepository.existsByEmail(updatedPostulante.getEmail())
                && !postulanteFromDb.getEmail().equals(updatedPostulante.getEmail())) {
            throw new RuntimeException("Unable to update. Email is already in use");
        }
        postulanteFromDb.setName(updatedPostulante.getName());
        postulanteFromDb.setLastName(updatedPostulante.getLastName());
        postulanteFromDb.setEmail(updatedPostulante.getEmail());
        postulanteFromDb.setPhone(updatedPostulante.getPhone());
        postulanteFromDb.setBirthday(updatedPostulante.getBirthday());
        postulanteFromDb.setPassword(updatedPostulante.getPassword());

        return postulanteRepository.save(postulanteFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Postulante postulante = postulanteRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found with id: " + id));
        postulanteRepository.delete(postulante);
    }
    @Transactional(readOnly = true)
    @Override
    public List<Postulante> filterByNameAndLastName(String name, String lastName) {
        return postulanteRepository.findByNameAndLastName(name, lastName);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Postulante> filterByAge(int age) {
        return postulanteRepository.findByAge(age);
    }
}
