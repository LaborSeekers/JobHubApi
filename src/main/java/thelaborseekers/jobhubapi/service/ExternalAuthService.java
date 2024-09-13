package thelaborseekers.jobhubapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.model.dto.ExternalAccountDto;
import thelaborseekers.jobhubapi.model.entity.Ofertante;
import thelaborseekers.jobhubapi.repository.OfertanteRepository;

@RequiredArgsConstructor
@Service
public class ExternalAuthService {

    private final OfertanteRepository ofertanteRepository;

    @Transactional
    public void registerExternalAccount(ExternalAccountDto externalAccountDto) {
        // Verifica si ya existe un ofertante con el mismo email
        boolean exists = ofertanteRepository.existsByEmail(externalAccountDto.getEmail());

        if (exists) {
            throw new RuntimeException("Ya existe una cuenta con el mismo email");
        }

        // Crea una nueva instancia de Ofertante y establece sus propiedades
        Ofertante ofertante = new Ofertante();
        ofertante.setName(externalAccountDto.getName());
        ofertante.setEmail(externalAccountDto.getEmail());
        ofertante.setPassword(generatePassword());
        ofertante.setPhone("");
        ofertante.setBirthday(null);

        // Guarda el ofertante en la base de datos
        ofertanteRepository.save(ofertante);
    }

    // Método para generar una contraseña por defecto
    private String generatePassword() {
        return "defaultPassword123";
    }
}

