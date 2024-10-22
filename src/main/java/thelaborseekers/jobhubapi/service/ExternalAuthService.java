package thelaborseekers.jobhubapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.model.dto.ExternalAccountDto;
import thelaborseekers.jobhubapi.model.entity.User;
import thelaborseekers.jobhubapi.repository.OfertanteRepository;
import thelaborseekers.jobhubapi.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class ExternalAuthService {

    private final OfertanteRepository ofertanteRepository;

    private final UserRepository userRepository;
    @Transactional
    public void registerExternalAccount(ExternalAccountDto externalAccountDto) {
        // Verifica si ya existe un ofertante con el mismo email
        boolean exists = userRepository.existsByEmail(externalAccountDto.getEmail());

        if (exists) {
            throw new RuntimeException("Ya existe una cuenta con el mismo email");
        }

        // Crea una nueva instancia de Ofertante y establece sus propiedades
        User user = new User();
        user.getOfertante().setFirstName(externalAccountDto.getName());
        user.setEmail(externalAccountDto.getEmail());
        user.setPassword(generatePassword());
        user.getOfertante().setPhone("");
        user.getOfertante().setBirthday(null);

        // Guarda el ofertante en la base de datos
        userRepository.save(user);
    }

    // Método para generar una contraseña por defecto
    private String generatePassword() {
        return "defaultPassword123";
    }
}

