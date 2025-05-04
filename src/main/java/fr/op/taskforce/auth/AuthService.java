package fr.op.taskforce.auth;

import fr.op.taskforce.auth.dto.UserLoginDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String verify(UserLoginDTO userLginDTO) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLginDTO.name(), userLginDTO.password())
        );

        if (!auth.isAuthenticated()) {
            throw new BadCredentialsException("Authentication failed for user: " + userLginDTO.name());
        }

        return jwtService.generateToken(userLginDTO.name());
    }
}
