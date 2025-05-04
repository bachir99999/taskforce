package fr.op.taskforce.auth;

import fr.op.taskforce.auth.dto.AuthResponseDTO;
import fr.op.taskforce.auth.dto.TokenRequestDTO;
import fr.op.taskforce.user.UserService;
import fr.op.taskforce.user.dto.UserDTO;
import fr.op.taskforce.auth.dto.UserLoginDTO;
import fr.op.taskforce.user.dto.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final JWTService jwtService;

    public AuthController(AuthService authService, UserService userService, JWTService jwtService) {
        this.authService = authService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUsers(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        var user = userService.findByName(userLoginDTO.name());
        var token = authService.verify(userLoginDTO);
        var response = new AuthResponseDTO(token, "Bearer", user);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyToken(@Valid @RequestBody TokenRequestDTO tokenRequestDTO) {
        var isValid = jwtService.isTokenValid(tokenRequestDTO.token());
        return ResponseEntity.ok(isValid);
    }

}
