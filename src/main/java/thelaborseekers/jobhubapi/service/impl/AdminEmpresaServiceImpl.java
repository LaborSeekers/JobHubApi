package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.dto.EmpresaDTO;
import thelaborseekers.jobhubapi.exception.BadRequestException;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.mapper.EmpresaMapper;
import thelaborseekers.jobhubapi.model.entity.Empresa;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.entity.Ofertante;
import thelaborseekers.jobhubapi.repository.EmpresaRepository;
import thelaborseekers.jobhubapi.repository.JobOfferRepository;
import thelaborseekers.jobhubapi.service.AdminEmpresaService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminEmpresaServiceImpl implements AdminEmpresaService {
    private final EmpresaRepository empresaRepository;
    private final EmpresaMapper empresaMapper;
    @Autowired
    private JobOfferRepository jobOfferRepository;

    @Transactional(readOnly = true)
    @Override
    public List<EmpresaDTO> findAll() {
        List<Empresa> empresas = empresaRepository.findAll();
        return empresas.stream().map(empresaMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<EmpresaDTO> paginate(Pageable pageable) {
        Page<Empresa> empresas = empresaRepository.findAll(pageable);
        return empresas.map(empresaMapper::toDTO);
    }


    @Transactional
    @Override
    public EmpresaDTO create(EmpresaDTO empresaDTO) {
        empresaRepository.findByName(empresaDTO.getName())
                .ifPresent(existingEmpresa ->{
                   throw new BadRequestException("Empresa already exists");
                });
        Empresa empresa = empresaMapper.toEntity(empresaDTO);
        empresa = empresaRepository.save(empresa);
        return empresaMapper.toDTO(empresa);
    }

    @Transactional(readOnly = true)
    @Override
    public EmpresaDTO findById(Integer id) {
        Empresa empresa = empresaRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Empresa not found with id:" + id));
        return empresaMapper.toDTO(empresa);
    }

    @Transactional
    @Override
    public EmpresaDTO update(Integer id, EmpresaDTO updatedEmpresaDTO){
        Empresa empresaFromDB = empresaRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Empresa not found with id: " + id));;

        empresaRepository.findByName(updatedEmpresaDTO.getName())
                .filter(existingEmpresa -> !existingEmpresa.getId().equals(id))
                .ifPresent(existingEmpresa -> {
                    throw new BadRequestException("Empresa already exists");
                });

        empresaFromDB.setName(updatedEmpresaDTO.getName());
        empresaFromDB.setDescription(updatedEmpresaDTO.getDescription());
        empresaFromDB = empresaRepository.save(empresaFromDB);
        return empresaMapper.toDTO(empresaFromDB);
    }

    public EmpresaDTO getEmpresaByJobOfferId(Integer jobOfferId) {
        // Buscar la oferta de trabajo por su ID
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new RuntimeException("JobOffer not found with id: " + jobOfferId));

        // Obtener el ofertante asociado con la oferta de trabajo
        Ofertante ofertante = jobOffer.getOfertante();

        // Si el ofertante tiene empresa asociada, devolver los datos de la empresa
        if (ofertante != null && ofertante.getEmpresa() != null) {
            Empresa empresa = ofertante.getEmpresa();
            // Convertir la empresa a DTO usando el  mapper
            return empresaMapper.toDTO(empresa);        }

        // Si no hay empresa asociada, puedes lanzar una excepción o devolver null
        throw new RuntimeException("Empresa not found for JobOffer id: " + jobOfferId);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Empresa empresa = empresaRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Empresa not found with id:" + id));
        empresaRepository.delete(empresa);
    }

}
