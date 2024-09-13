package thelaborseekers.jobhubapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thelaborseekers.jobhubapi.model.entity.Postulante;
import thelaborseekers.jobhubapi.repository.PostulanteRepository;

@Service
public class AuthService {

    @Autowired
    private PostulanteRepository postulanteRepository;

    public String registerWithExternalAccount(String token) {
        // Verificar el token con el proveedor externo y obtener la información del usuario
        // Ejemplo de datos obtenidos: email, name, etc.
        String emailFromToken = "test@example.com";  // Simulación de obtención de datos desde el token
        String nameFromToken = "John Doe";          // Simulación de obtención de datos desde el token

        // Verificar si el email ya está registrado
        boolean exists = postulanteRepository.existsByEmail(emailFromToken);
        if (exists) {
            return "La cuenta ya está registrada. Por favor, inicie sesión.";
        }

        // Registrar el nuevo usuario
        Postulante newPostulante = new Postulante();
        newPostulante.setEmail(emailFromToken);
        newPostulante.setName(nameFromToken);
        // Otros campos necesarios

        postulanteRepository.save(newPostulante);
        return "Registro exitoso. Bienvenido a JobHub!";
    }
}
