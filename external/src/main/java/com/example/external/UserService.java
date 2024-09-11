package com.example.external;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public Optional<User> findUserByEmail(String email) {
        return userRepository
                .findByEmail(email);
    }

    public User save(RegisterRequest registerRequest) {
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .name(registerRequest.getName())
                .createdAt(now)
                .updatedAt(now)
                .email(registerRequest.getEmail())
                .pass(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();
        return userRepository.save(user);
    }
}
