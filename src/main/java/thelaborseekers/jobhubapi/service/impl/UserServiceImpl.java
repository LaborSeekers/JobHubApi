package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import thelaborseekers.jobhubapi.dto.AuthResponseDTO;
import thelaborseekers.jobhubapi.dto.LoginDTO;
import thelaborseekers.jobhubapi.dto.UserProfileDTO;
import thelaborseekers.jobhubapi.dto.UserRegistrationDTO;
import thelaborseekers.jobhubapi.exception.BadRequestException;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.mapper.UserMapper;
import thelaborseekers.jobhubapi.model.entity.Ofertante;
import thelaborseekers.jobhubapi.model.entity.Postulante;
import thelaborseekers.jobhubapi.model.entity.Role;
import thelaborseekers.jobhubapi.model.entity.User;
import thelaborseekers.jobhubapi.model.enums.Erole;
import thelaborseekers.jobhubapi.model.enums.Reputation;
import thelaborseekers.jobhubapi.repository.OfertanteRepository;
import thelaborseekers.jobhubapi.repository.PostulanteRepository;
import thelaborseekers.jobhubapi.repository.RoleRepository;
import thelaborseekers.jobhubapi.repository.UserRepository;
import thelaborseekers.jobhubapi.security.TokenProvider;
import thelaborseekers.jobhubapi.security.UserPrincipal;
import thelaborseekers.jobhubapi.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PostulanteRepository postulanteRepository;
    private final OfertanteRepository ofertanteRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Override
    public List<UserProfileDTO> getAllUserProfiles(){
       List<User> users = userRepository.findAll();
       return users.stream().map(userMapper::toUserProfileDTO).toList();
    }

    @Override
    public UserProfileDTO registerPostulante(UserRegistrationDTO userRegistrationDTO) {
        return registerUserWithRole(userRegistrationDTO,Erole.POSTULANTE);
    }

    @Override
    public UserProfileDTO registerOfertante(UserRegistrationDTO userRegistrationDTO) {
        return registerUserWithRole(userRegistrationDTO,Erole.OFERTANTE);
    }

    @Override
    public AuthResponseDTO login(LoginDTO loginDTO) {
        //Autenticar al usuario utilizando AuthManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        //Una vez se realiza la autenticacion, el objeto authentication contiene info del usuario autenticado
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();
        String token = tokenProvider.createAccessToken(authentication);
        AuthResponseDTO ResponseDTO = userMapper.toAuthResponseDTO(user,token);

        return ResponseDTO;
    }

    @Override
    public UserProfileDTO updateUserProfile(Integer id, UserProfileDTO userProfileDTO) {
        // Buscar el usuario por su ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Verificar si ya existe un postulante u ofertante con el mismo nombre y apellido (excepto el usuario actual)
        // La verificación se realiza excluyendo el usuario actual para permitir que actualice su propio perfil
        // sin que se genere un conflicto de duplicidad en los nombres y apellidos.
        boolean existsAsCustomer = postulanteRepository.existsByFirstNameAndLastNameAndUserIdNot(userProfileDTO.getFirstName(), userProfileDTO.getLastName(), id);
        boolean existsAsAuthor = ofertanteRepository.existsByFirstNameAndLastNameAndUserIdNot(userProfileDTO.getFirstName(), userProfileDTO.getLastName(), id);

        if (existsAsCustomer || existsAsAuthor) {
            throw new BadRequestException("Ya existe un usuario con el mismo nombre y apellido");
        }


        // Actualizar los campos específicos del perfil
        if (user.getPostulante() != null) {
            user.getPostulante().setFirstName(userProfileDTO.getFirstName());
            user.getPostulante().setLastName(userProfileDTO.getLastName());
            user.getPostulante().setPhone(userProfileDTO.getPhone());
            user.getPostulante().setBirthday(userProfileDTO.getBirthday());
            user.getPostulante().setUpdatedAt(LocalDateTime.now());
        }

        if (user.getOfertante() != null) {
            user.getOfertante().setFirstName(userProfileDTO.getFirstName());
            user.getOfertante().setLastName(userProfileDTO.getLastName());
            user.getOfertante().setPhone(userProfileDTO.getPhone());
            user.getOfertante().setBirthday(userProfileDTO.getBirthday());
            user.getOfertante().setUpdatedAt(LocalDateTime.now());
            //evaluar si es que es necesario hacer el update de la empresa por este medio
        }
        // Guardar los cambios en la base de datos
        User updatedUser = userRepository.save(user);

        // Convertir el usuario actualizado a UserProfileDTO para la respuesta
        return userMapper.toUserProfileDTO(updatedUser);
    }

    @Override
    public UserProfileDTO getUserProfile(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Convertir a UserProfileDTO para la respuesta
        return userMapper.toUserProfileDTO(user);
    }

    private UserProfileDTO registerUserWithRole(UserRegistrationDTO userRegistrationDTO, Erole roleEnum) {
        //Verificar si el email ya esta en uso o si ya existe un usuario con el mismo nombre y apellido
        boolean existByEmail = userRepository.existsByEmail(userRegistrationDTO.getEmail());
        boolean existsAsPostulante = postulanteRepository.existsByFirstNameAndLastName(userRegistrationDTO.getFirstName(),userRegistrationDTO.getLastName());
        boolean existAsOfertante = ofertanteRepository.existsByFirstNameAndLastName(userRegistrationDTO.getFirstName(),userRegistrationDTO.getLastName());
        if(existByEmail) {
            throw new BadRequestException("Email already exists");
        }
        if(existsAsPostulante || existAsOfertante) {
            throw new BadRequestException("User already exists");
        }
        Role role = roleRepository.findByName(roleEnum)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        userRegistrationDTO.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        User user = userMapper.toEntity(userRegistrationDTO);
        user.setRole(role);
        if(roleEnum == Erole.POSTULANTE){
            Postulante postulante = new Postulante();
            postulante.setFirstName(userRegistrationDTO.getFirstName());
            postulante.setLastName(userRegistrationDTO.getLastName());
            postulante.setPhone(userRegistrationDTO.getPhone());
            postulante.setBirthday(userRegistrationDTO.getBirthday());
            postulante.setCreatedAt(LocalDateTime.now());

            postulante.setUser(user);
            user.setPostulante(postulante);
        } else if (roleEnum == Erole.OFERTANTE) {
            Ofertante ofertante = new Ofertante();
            ofertante.setFirstName(userRegistrationDTO.getFirstName());
            ofertante.setLastName(userRegistrationDTO.getLastName());
            ofertante.setPhone(userRegistrationDTO.getPhone());
            ofertante.setBirthday(userRegistrationDTO.getBirthday());
            ofertante.setCreatedAt(LocalDateTime.now());
            ofertante.setEmpresa(userRegistrationDTO.getEmpresa());
            ofertante.setReputationValue(100);
            ofertante.setReputation(Reputation.valueOf("ALTA"));
            ofertante.setUser(user);
            user.setOfertante(ofertante);
        }
        User savedUser = userRepository.save(user);
        return userMapper.toUserProfileDTO(savedUser);
    }

}
