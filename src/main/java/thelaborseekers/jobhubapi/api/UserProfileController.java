package thelaborseekers.jobhubapi.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.dto.UserProfileDTO;
import thelaborseekers.jobhubapi.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user/profile")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserService userService;

    //obtener el perfil de usuario por su ID
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable Integer id) {
        UserProfileDTO userProfileDTO = userService.getUserProfile(id);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.OK);
    }

    //Actualizar el perfil de usuario
    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDTO> updateProfile(@PathVariable Integer id, @Valid @RequestBody UserProfileDTO userProfileDTO) {
        UserProfileDTO updatedProfile = userService.updateUserProfile(id, userProfileDTO);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserProfileDTO>> getAllUserProfiles() {
        List<UserProfileDTO> userProfileDTOs = userService.getAllUserProfiles();
        return new ResponseEntity<>(userProfileDTOs, HttpStatus.OK);
    }
}
