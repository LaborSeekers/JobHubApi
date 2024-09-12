package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.model.entity.Ofertante;
import thelaborseekers.jobhubapi.repository.OfertanteRepository;
import thelaborseekers.jobhubapi.service.AdminOfertanteService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminOfertanteServiceImpl implements AdminOfertanteService {
    private final OfertanteRepository ofertanteRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Ofertante> findAll() {return ofertanteRepository.findAll();}

    @Transactional(readOnly = true)
    @Override
    public Page<Ofertante> paginate(Pageable pageable) {
        return ofertanteRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Ofertante create(Ofertante ofertante) {
        if(ofertanteRepository.existsByEmail(ofertante.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }
        return ofertanteRepository.save(ofertante);
    }

    @Transactional(readOnly = true)
    @Override
    public Ofertante findById(Integer id) {
        return ofertanteRepository.findById(id).orElseThrow(()->new RuntimeException("Ofertante not found with id: " + id));
    }

    @Transactional
    @Override
    public Ofertante update(Integer id, Ofertante updatedOfertante) {
        Ofertante ofertanteFromDB = findById(id);

        if(ofertanteRepository.existsByEmail(updatedOfertante.getEmail())
                && !ofertanteFromDB.getEmail().equals(updatedOfertante.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }
        ofertanteFromDB.setName(updatedOfertante.getName());
        ofertanteFromDB.setLastName(updatedOfertante.getLastName());
        ofertanteFromDB.setEmail(updatedOfertante.getEmail());
        ofertanteFromDB.setPhone(updatedOfertante.getPhone());
        ofertanteFromDB.setBirthday(updatedOfertante.getBirthday());
        ofertanteFromDB.setPassword(updatedOfertante.getPassword());
        ofertanteFromDB.setEmpresa(updatedOfertante.getEmpresa());

        return ofertanteRepository.save(ofertanteFromDB);
    }
    @Transactional
    @Override
    public Ofertante updateReputation(Integer id, Integer reputation) {
        Ofertante ofertanteFromDB = findById(id);

        ofertanteFromDB.setReputationValue(reputation);

        return ofertanteRepository.save(ofertanteFromDB);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Ofertante Ofertante = ofertanteRepository.findById(id).orElseThrow(()->new RuntimeException("Ofertante not found with id: " + id));
        ofertanteRepository.delete(Ofertante);
    }
}
