package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.repository.OfertaLaboralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfertaLaboralService {

    @Autowired
    private OfertaLaboralRepository ofertaLaboralRepository;

    public List<JobOffer> filtrarOfertas(String location, String jobModality) {
        try {
            return ofertaLaboralRepository.findByLocationAndModality(location, jobModality);
        } catch (Exception e) {
            throw new RuntimeException("Error al aplicar los filtros: " + e.getMessage());
        }
    }
}
