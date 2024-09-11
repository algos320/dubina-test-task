package com.example.external;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
public class UserAuthToken extends UsernamePasswordAuthenticationToken {
    private final Long userId;
    public UserAuthToken(Object principal, Object credentials, Long userId) {
        super(principal, credentials);
        this.userId = userId;
    }
}
