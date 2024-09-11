package com.example.external;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse auth(AuthRequest authRequest) {
        User user = userService
                .findUserByEmail(authRequest.getEmail())
                .orElseThrow(() -> new ErrorCodeException("User not found", 404));
        authenticate(authRequest, user);

        String token = jwtService.generateToken(Collections.EMPTY_MAP, user);
        return new AuthResponse(token);
    }

    private void authenticate(AuthRequest authRequest, User account) {
        try {
            authenticationManager.authenticate(
                    new UserAuthToken(
                            authRequest.getEmail(),
                            authRequest.getPassword(),
                            account.getId()
                    )
            );
        } catch (BadCredentialsException e) {
            log.error("", e);
            throw new ErrorCodeException("User not found", 401);
        }
    }


    public AuthResponse register(RegisterRequest registerRequest) {
        User account = userService.save(registerRequest);
        String token = jwtService.generateToken(Collections.emptyMap(), account);

        return new AuthResponse(token);
    }
}
