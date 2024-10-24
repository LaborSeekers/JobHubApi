package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.dto.PostulanteRegisterDTO;
import thelaborseekers.jobhubapi.dto.PostulacionDTO;
import thelaborseekers.jobhubapi.dto.PostulanteHistorialDTO;
import thelaborseekers.jobhubapi.dto.PostulanteProfileDTO;
import thelaborseekers.jobhubapi.exception.BadRequestException;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.mapper.PostulanteMapper;
import thelaborseekers.jobhubapi.mapper.PostulacionMapper;
import thelaborseekers.jobhubapi.model.entity.Postulacion;
import thelaborseekers.jobhubapi.model.entity.Postulante;
import thelaborseekers.jobhubapi.repository.PostulanteRepository;
import thelaborseekers.jobhubapi.repository.PostulacionRepository;
import thelaborseekers.jobhubapi.repository.UserRepository;
import thelaborseekers.jobhubapi.service.AdminPostulanteService;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminPostulanteServiceImpl implements AdminPostulanteService {
    private final UserRepository userRepository;
    private final PostulanteRepository postulanteRepository;
    private final PostulacionRepository postulacionRepository;
    private final PostulanteMapper postulanteMapper;
    private final PostulacionMapper postulacionMapper;

    @Transactional(readOnly = true)
    @Override
    public List<PostulanteProfileDTO> findAll() {
        List<Postulante> postulantes = postulanteRepository.findAll();
        return postulantes.stream().map(postulanteMapper::toPostulanteProfileDTO).toList();
    }


    @Transactional(readOnly = true)
    @Override
    public Page<PostulanteRegisterDTO> paginate(Pageable pageable) {
        Page<Postulante> postulantes = postulanteRepository.findAll(pageable);
        return postulantes.map(postulanteMapper::toDTO);
    }

    @Transactional
    @Override
    public PostulanteRegisterDTO create(PostulanteRegisterDTO postulanteRegisterDTO) {
        if(userRepository.existsByEmail(postulanteRegisterDTO.getEmail())) {
            throw new BadRequestException("Email is already in use");
        }
        Postulante postulante = postulanteMapper.toEntity(postulanteRegisterDTO);
        postulante.setActive(false);
        postulante.setCreatedAt(LocalDateTime.now());
        postulante = postulanteRepository.save(postulante);
        return postulanteMapper.toDTO(postulante);
    }

    @Transactional(readOnly = true)
    @Override
    public PostulanteProfileDTO findById(Integer id) {
        Postulante postulante = postulanteRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with id: " + id));
        return postulanteMapper.toPostulanteProfileDTO(postulante);
    }

    @Transactional
    @Override
    public PostulanteRegisterDTO update(Integer id, PostulanteRegisterDTO updatedPostulanteRegisterDTO) {
        Postulante postulanteFromDb =postulanteRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with id: " + id));

        if (userRepository.existsByEmail(updatedPostulanteRegisterDTO.getEmail())
                && !postulanteFromDb.getUser().getEmail().equals(updatedPostulanteRegisterDTO.getEmail())) {
            throw new BadRequestException("Unable to update. Email is already in use");
        }
        postulanteFromDb.setFirstName(updatedPostulanteRegisterDTO.getFirstName());
        postulanteFromDb.setLastName(updatedPostulanteRegisterDTO.getLastName());
        //postulanteFromDb.setEmail(updatedPostulanteRegisterDTO.getEmail());
        postulanteFromDb.setPhone(updatedPostulanteRegisterDTO.getPhone());
        postulanteFromDb.setBirthday(updatedPostulanteRegisterDTO.getBirthday());
        //postulanteFromDb.setPassword(updatedPostulanteRegisterDTO.getPassword());

        postulanteFromDb.setUpdatedAt(LocalDateTime.now());
        postulanteFromDb = postulanteRepository.save(postulanteFromDb);
        return postulanteMapper.toDTO(postulanteFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Postulante postulante = postulanteRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with id: " + id));
        postulanteRepository.delete(postulante);
    }
    @Transactional(readOnly = true)
    @Override
    public List<PostulanteProfileDTO> filterByNameAndLastName(String name, String lastName) {
        List<Postulante> postulantes = postulanteRepository.findByFirstNameAndLastName(name,lastName);
        return postulantes.stream().map(postulanteMapper::toPostulanteProfileDTO).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostulanteProfileDTO> filterByAge(int age) {
        List<Postulante> postulantes = postulanteRepository.findByAge(age);
        return postulantes.stream().map(postulanteMapper::toPostulanteProfileDTO).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public PostulanteHistorialDTO getHistorialPostulaciones(Integer postulanteId) {
        // Buscar todas las postulaciones de un postulante
        List<Postulacion> postulaciones = postulacionRepository.findByPostulanteId(postulanteId);
        
        // Validar si el postulante tiene postulaciones
        if (postulaciones.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron postulaciones para el postulante con id: " + postulanteId);
        }

        // Mapear las postulaciones a DTOs
        List<PostulacionDTO> postulacionDTOs = postulaciones.stream()
                .map(postulacionMapper::toDTO)
                .toList();

        // Crear el DTO de historial y asignar los valores
        PostulanteHistorialDTO historialDTO = new PostulanteHistorialDTO();
        historialDTO.setCantidadPostulaciones(postulacionDTOs.size());
        historialDTO.setPostulaciones(postulacionDTOs);

        return historialDTO;
    }
}
