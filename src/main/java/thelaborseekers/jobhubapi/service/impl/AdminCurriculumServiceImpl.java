package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.model.entity.Curriculum;
import thelaborseekers.jobhubapi.model.entity.Postulante;
import thelaborseekers.jobhubapi.repository.CurriculumRepository;
import thelaborseekers.jobhubapi.repository.PostulanteRepository;
import thelaborseekers.jobhubapi.service.AdminCurriculumService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminCurriculumServiceImpl implements AdminCurriculumService {
    private final CurriculumRepository curriculumRepository;
    private final PostulanteRepository postulanteRepository;
    
    @Transactional(readOnly = true)
    @Override
    public List<Curriculum> findAll() {
        return curriculumRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Curriculum> paginate(Pageable pageable) {
        return curriculumRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Curriculum create(Curriculum curriculum) {
        if (curriculum.getPostulante() == null || curriculum.getPostulante().getId() == null) {
        throw new RuntimeException("Postulante ID is required");
    }

    // Obtener el postulante a partir de su ID
    Postulante postulante = postulanteRepository.findById(curriculum.getPostulante().getId())
            .orElseThrow(() -> new RuntimeException("Postulante not found with id: " + curriculum.getPostulante().getId()));

    // Asignar el postulante al curriculum
    curriculum.setPostulante(postulante);

    // Guardar el currículum con el postulante asociado
    return curriculumRepository.save(curriculum);
    }

    @Transactional(readOnly = true)
    @Override
    public Curriculum findById(Integer id) {
        return curriculumRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Curriculum not found with id:" + id));
    }

    @Transactional
    @Override
    public Curriculum update(Integer id, Curriculum updatedCurriculum) {
        Curriculum curriculumFromDB = findById(id);
        curriculumFromDB.setContent(updatedCurriculum.getContent());
        curriculumFromDB.setPostulante(updatedCurriculum.getPostulante());
        return curriculumRepository.save(curriculumFromDB);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Curriculum curriculum = findById(id);
        curriculumRepository.delete(curriculum);
    }
}