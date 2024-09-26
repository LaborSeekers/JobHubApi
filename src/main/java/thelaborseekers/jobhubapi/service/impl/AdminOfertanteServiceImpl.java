package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.dto.OfertanteProfileDTO;
import thelaborseekers.jobhubapi.dto.OfertanteRegisterDTO;
import thelaborseekers.jobhubapi.exception.BadRequestException;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.mapper.OfertanteMapper;
import thelaborseekers.jobhubapi.model.entity.Ofertante;
import thelaborseekers.jobhubapi.repository.OfertanteRepository;
import thelaborseekers.jobhubapi.service.AdminOfertanteService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminOfertanteServiceImpl implements AdminOfertanteService {
    private final OfertanteRepository ofertanteRepository;
    private final OfertanteMapper ofertanteMapper;

    @Transactional(readOnly = true)
    @Override
    public List<OfertanteProfileDTO> findAll() {
        List<Ofertante> ofertantes = ofertanteRepository.findAll();
        return ofertantes.stream().map(ofertanteMapper::toOfertanteProfileDTO).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<OfertanteProfileDTO> paginate(Pageable pageable) {
        Page<Ofertante> ofertantes = ofertanteRepository.findAll(pageable);
        return ofertantes.map(ofertanteMapper::toOfertanteProfileDTO);
    }

    @Transactional
    @Override
    public OfertanteRegisterDTO create(OfertanteRegisterDTO ofertanteRegisterDTO) {
        if(ofertanteRepository.existsByEmail(ofertanteRegisterDTO.getEmail())) {
            throw new BadRequestException("Email is already in use");
        }
        Ofertante ofertante = ofertanteMapper.toEntity(ofertanteRegisterDTO);
        ofertante.setReputationValue(100);
        ofertante = ofertanteRepository.save(ofertante);
        return ofertanteMapper.toDTO(ofertante);
    }

    @Transactional(readOnly = true)
    @Override
    public OfertanteProfileDTO findById(Integer id) {
        Ofertante ofertante = ofertanteRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Ofertante not found with id: " + id));
        return ofertanteMapper.toOfertanteProfileDTO(ofertante);
    }

    @Transactional
    @Override
    public OfertanteRegisterDTO update(Integer id, OfertanteRegisterDTO updatedOfertanteRegisterDTO) {
        Ofertante ofertanteFromDB = ofertanteRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Ofertante not found with id: " + id));

        if(ofertanteRepository.existsByEmail(updatedOfertanteRegisterDTO.getEmail())
                && !ofertanteFromDB.getEmail().equals(updatedOfertanteRegisterDTO.getEmail())) {
            throw new BadRequestException("Email is already in use");
        }
        ofertanteFromDB.setName(updatedOfertanteRegisterDTO.getName());
        ofertanteFromDB.setLastName(updatedOfertanteRegisterDTO.getLastName());
        ofertanteFromDB.setEmail(updatedOfertanteRegisterDTO.getEmail());
        ofertanteFromDB.setPhone(updatedOfertanteRegisterDTO.getPhone());
        ofertanteFromDB.setBirthday(updatedOfertanteRegisterDTO.getBirthday());
        ofertanteFromDB.setPassword(updatedOfertanteRegisterDTO.getPassword());
        ofertanteFromDB.setEmpresa(updatedOfertanteRegisterDTO.getEmpresa());

        ofertanteFromDB = ofertanteRepository.save(ofertanteFromDB);
        return ofertanteMapper.toDTO(ofertanteFromDB);
    }


    //Servicio para operar la reputacion (0 malo y 1 bueno)
    @Transactional
    @Override
    public Ofertante updateReputation(Integer id, Integer ratingValue) {
        Ofertante ofertanteFromDB = ofertanteRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Ofertante not found with id: " + id));;
        Integer currentReputation = ofertanteFromDB.getReputationValue();

        // Actualizar la reputación
        if (ratingValue == 1) {
            ofertanteFromDB.setReputationValue(Math.min(currentReputation + 2, 100)); //max=100
        } else if (ratingValue == 0) {
            // Decrementar reputación en 2 puntos si ratingValue es 0
            ofertanteFromDB.setReputationValue(Math.max(currentReputation - 2, 0)); //min=0
        }
        //discutir si es necesario usar dto en esta funcion
        return ofertanteRepository.save(ofertanteFromDB);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Ofertante Ofertante = ofertanteRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Ofertante not found with id: " + id));
        ofertanteRepository.delete(Ofertante);
    }
}
