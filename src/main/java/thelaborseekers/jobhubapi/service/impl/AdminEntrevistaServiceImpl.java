package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.dto.EntrevistaCreateDTO;
import thelaborseekers.jobhubapi.dto.EntrevistaDetailDTO;
import thelaborseekers.jobhubapi.exception.BadRequestException;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.mapper.EntrevistaMapper; // Asumiendo que necesitas un mapper para convertir entre DTOs y entidades
import thelaborseekers.jobhubapi.model.entity.Entrevista; // Entidad Entrevista
import thelaborseekers.jobhubapi.model.entity.Ofertante;
import thelaborseekers.jobhubapi.model.entity.Postulante;
import thelaborseekers.jobhubapi.repository.EntrevistaRepository; // Repositorio de Entrevista
import thelaborseekers.jobhubapi.repository.OfertanteRepository;
import thelaborseekers.jobhubapi.repository.PostulanteRepository;
import thelaborseekers.jobhubapi.service.AdminEntrevistaService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminEntrevistaServiceImpl implements AdminEntrevistaService {

    private final EntrevistaRepository entrevistaRepository;
    private final EntrevistaMapper entrevistaMapper;
    private final PostulanteRepository postulanteRepository;
    private final OfertanteRepository ofertanteRepository;

    @Override
    @Transactional
    public EntrevistaDetailDTO createInterview(EntrevistaCreateDTO entrevistaCreateDTO) {


        // Verificar si el postulante tiene una entrevista en la misma fecha
        Optional<Entrevista> existingPostulanteInterview = entrevistaRepository.findByPostulanteIdAndInterviewDate(
                entrevistaCreateDTO.getPostulanteId(), entrevistaCreateDTO.getInterviewDate());

        if (existingPostulanteInterview.isPresent()) {
            throw new BadRequestException("El postulante ya tiene una entrevista programada en la misma fecha.");
        }

        // Verificar si el ofertante tiene una entrevista en la misma fecha
        Optional<Entrevista> existingOfertanteInterview = entrevistaRepository.findByOfertanteIdAndInterviewDate(
                entrevistaCreateDTO.getOfertanteId(), entrevistaCreateDTO.getInterviewDate());

        if (existingOfertanteInterview.isPresent()) {
            throw new BadRequestException("El ofertante ya tiene una entrevista programada en la misma fecha.");
        }
        // Asignar Id Foreign Keys
        Postulante postulante = postulanteRepository.findById(entrevistaCreateDTO.getPostulanteId()).orElseThrow(() ->
                new ResourceNotFoundException("Postulante no encontrado con ID: " + entrevistaCreateDTO.getPostulanteId()));

        Ofertante ofertante = ofertanteRepository.findById(entrevistaCreateDTO.getOfertanteId()).orElseThrow(() ->
                new ResourceNotFoundException("Ofertante no encontrado con ID: " + entrevistaCreateDTO.getOfertanteId()));
        // Convertir DTO a entidad
        Entrevista entrevista = entrevistaMapper.toEntrevista(entrevistaCreateDTO);
        // Asignar las entidades relacionadas
        entrevista.setPostulante(postulante);
        entrevista.setOfertante(ofertante);

        return entrevistaMapper.toEntrevistaDetailDTO(entrevistaRepository.save(entrevista));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntrevistaDetailDTO> getAllScheduledInterviewsForOfertante(Integer ofertanteId) {
        List<Entrevista> entrevistas = entrevistaRepository.findAllByOfertanteId(ofertanteId);
        return entrevistas.stream().map(entrevistaMapper::toEntrevistaDetailDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntrevistaDetailDTO> getAllScheduledInterviewsForPostulante(Integer postulanteId) {
        List<Entrevista> entrevistas = entrevistaRepository.findAllByPostulanteId(postulanteId);
        return entrevistas.stream().map(entrevistaMapper::toEntrevistaDetailDTO).toList();
    }

        @Override
        @Transactional
        public EntrevistaDetailDTO updateInterview(Integer entrevistaId, EntrevistaCreateDTO entrevistaUpdateDTO) {


        // Buscar la entrevista existente
            Entrevista entrevista = entrevistaRepository.findById(entrevistaId)
                    .orElseThrow(() -> new ResourceNotFoundException("Entrevista no encontrada con ID: " + entrevistaId));

            // Verificar si el postulante tiene otra entrevista en la misma fecha
            if (entrevistaUpdateDTO.getPostulanteId() != null) {
                Optional<Entrevista> existingPostulanteInterview = entrevistaRepository.findByPostulanteIdAndInterviewDate(
                        entrevistaUpdateDTO.getPostulanteId(), entrevistaUpdateDTO.getInterviewDate());

                if (existingPostulanteInterview.isPresent() && !existingPostulanteInterview.get().getId().equals(entrevistaId)) {
                    throw new BadRequestException("El postulante ya tiene una entrevista programada en la misma fecha.");
                }
            }

            // Verificar si el ofertante tiene otra entrevista en la misma fecha
            if (entrevistaUpdateDTO.getOfertanteId() != null) {
                Optional<Entrevista> existingOfertanteInterview = entrevistaRepository.findByOfertanteIdAndInterviewDate(
                        entrevistaUpdateDTO.getOfertanteId(), entrevistaUpdateDTO.getInterviewDate());

                if (existingOfertanteInterview.isPresent() && !existingOfertanteInterview.get().getId().equals(entrevistaId)) {
                    throw new BadRequestException("El ofertante ya tiene una entrevista programada en la misma fecha.");
                }
            }

            // Asignar FK antes de actualizar
            Postulante postulante = postulanteRepository.findById(entrevistaUpdateDTO.getPostulanteId()).orElseThrow(() ->
                    new ResourceNotFoundException("Postulante no encontrado con ID: " + entrevistaUpdateDTO.getPostulanteId()));

            Ofertante ofertante = ofertanteRepository.findById(entrevistaUpdateDTO.getOfertanteId()).orElseThrow(() ->
                    new ResourceNotFoundException("Ofertante no encontrado con ID: " + entrevistaUpdateDTO.getOfertanteId()));

            // Actualización de los campos de la entrevista
            entrevista.setInterviewDate(entrevistaUpdateDTO.getInterviewDate());
            entrevista.setStatus(entrevistaUpdateDTO.getStatus());
            entrevista.setModality(entrevistaUpdateDTO.getModality());
            entrevista.setInterviewPlatform(entrevistaUpdateDTO.getInterviewPlatform());
            entrevista.setInterviewLink(entrevistaUpdateDTO.getInterviewLink());
            entrevista.setPostulante(postulante);
            entrevista.setOfertante(ofertante);

            return entrevistaMapper.toEntrevistaDetailDTO(entrevistaRepository.save(entrevista));
        }


    @Override
    @Transactional
    public void deleteInterview(Integer entrevistaId) {
        // Obtén la entidad Entrevista desde el repositorio
        Entrevista entrevista = entrevistaRepository.findById(entrevistaId)
                .orElseThrow(() -> new ResourceNotFoundException("Entrevista no encontrada con ID: " + entrevistaId));

        // Elimina la entidad
        entrevistaRepository.delete(entrevista);
    }

    @Override
    public String enterInterview(Integer entrevistaId) {
        Entrevista entrevista = entrevistaRepository.findById(entrevistaId)
                .orElseThrow(() -> new ResourceNotFoundException("Entrevista no encontrada con ID: " + entrevistaId));

        // Lógica para verificar si la entrevista está próxima a comenzar
        return entrevista.getInterviewLink(); // Devuelve el enlace para ingresar
    }
}
