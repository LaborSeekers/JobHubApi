package thelaborseekers.jobhubapi.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thelaborseekers.jobhubapi.dto.PostulacionDTO;
import thelaborseekers.jobhubapi.model.entity.Postulacion;
import thelaborseekers.jobhubapi.repository.PostulanteRepository;
import thelaborseekers.jobhubapi.repository.JobOfferRepository;

@Component
public class PostulacionMapper {
    private final ModelMapper modelMapper;
    private final PostulanteRepository postulanteRepository;
    private final JobOfferRepository jobOfferRepository;

    @Autowired
    public PostulacionMapper(ModelMapper modelMapper, PostulanteRepository postulanteRepository, JobOfferRepository jobOfferRepository) {
        this.modelMapper = modelMapper;
        this.postulanteRepository = postulanteRepository;
        this.jobOfferRepository = jobOfferRepository;
    }

    public PostulacionDTO toDTO(Postulacion postulacion) {
        PostulacionDTO postulacionDTO = modelMapper.map(postulacion, PostulacionDTO.class);
        
         // Asignar el nombre completo y el título desde entidades relacionadas
         postulacionDTO.setPostulanteNombre(postulacion.getPostulante().getFirstName() + " " + postulacion.getPostulante().getLastName());
         postulacionDTO.setOfertaLaboralTitulo(postulacion.getOfertaLaboral().getTitle());
         
         // Convertir los IDs a Long y asignarlos al DTO
         postulacionDTO.setPostulanteId(postulacion.getPostulante().getId().longValue());
         postulacionDTO.setOfertaLaboralId(postulacion.getOfertaLaboral().getId().longValue());

        return postulacionDTO;
    }
    public Postulacion toEntity(PostulacionDTO postulacionDTO) {
        Postulacion postulacion = modelMapper.map(postulacionDTO, Postulacion.class);

        // Asigna el postulante usando el ID de `postulanteId` en el DTO
        if (postulacionDTO.getPostulanteId() != null) {
            postulanteRepository.findById(postulacionDTO.getPostulanteId().intValue()) // Convertir Long a Integer
                    .ifPresent(postulacion::setPostulante);
        }

        // Asigna la oferta laboral usando el ID de `ofertaLaboralId` en el DTO
        if (postulacionDTO.getOfertaLaboralId() != null) {
            jobOfferRepository.findById(postulacionDTO.getOfertaLaboralId().intValue()) // Convertir Long a Integer
                    .ifPresent(postulacion::setOfertaLaboral);
        }

        return postulacion;
    }

}
