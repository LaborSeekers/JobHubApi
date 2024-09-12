package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.model.entity.Empresa;
import thelaborseekers.jobhubapi.repository.EmpresaRepository;
import thelaborseekers.jobhubapi.service.AdminEmpresaService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminEmpresaServiceImpl implements AdminEmpresaService {
    private final EmpresaRepository empresaRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Empresa> findAll() {return empresaRepository.findAll();}

    @Transactional(readOnly = true)
    @Override
    public Page<Empresa> paginate(Pageable pageable) {return empresaRepository.findAll(pageable);}


    @Transactional
    @Override
    public Empresa create(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Transactional(readOnly = true)
    @Override
    public Empresa findById(Integer id) {
        return empresaRepository.findById(id).orElseThrow(()->new RuntimeException("Empresa not found with id:" + id));
    }

    @Transactional
    @Override
    public Empresa update(Integer id, Empresa updatedEmpresa){
        Empresa empresaFromDB = findById(id);

        empresaFromDB.setName(updatedEmpresa.getName());
        empresaFromDB.setDescription(updatedEmpresa.getDescription());
        return empresaRepository.save(empresaFromDB);
    }
    @Transactional
    @Override
    public void delete(Integer id) {
        Empresa empresa = empresaRepository.findById(id).orElseThrow(()->new RuntimeException("Empresa not found with id:" + id));
        empresaRepository.delete(empresa);
    }

}
