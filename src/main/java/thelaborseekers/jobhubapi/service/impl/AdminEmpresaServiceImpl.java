package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.dto.EmpresaDTO;
import thelaborseekers.jobhubapi.exception.BadRequestException;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.mapper.EmpresaMapper;
import thelaborseekers.jobhubapi.model.entity.Empresa;
import thelaborseekers.jobhubapi.repository.EmpresaRepository;
import thelaborseekers.jobhubapi.service.AdminEmpresaService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminEmpresaServiceImpl implements AdminEmpresaService {
    private final EmpresaRepository empresaRepository;
    private final EmpresaMapper empresaMapper;

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
    @Transactional
    @Override
    public void delete(Integer id) {
        Empresa empresa = empresaRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Empresa not found with id:" + id));
        empresaRepository.delete(empresa);
    }

}
