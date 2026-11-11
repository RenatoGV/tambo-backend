package com.equipo.tambo.security;

import com.equipo.tambo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserStatusTokenValidator implements OAuth2TokenValidator<Jwt> {
    private final UserRepository userRepository;

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        String username = jwt.getSubject();

        if(username == null || username.isBlank()) {
            return invalidToken();
        }

        boolean active = userRepository.findByUser(username)
                .filter(user -> user.isActive())
                .isPresent();

        if(!active) {
            return invalidToken();
        }

        return OAuth2TokenValidatorResult.success();
    }

    private OAuth2TokenValidatorResult invalidToken() {
        OAuth2Error error = new OAuth2Error(
                OAuth2ErrorCodes.INVALID_TOKEN,
                "El usuario no existe o se encuentra inactivo",
                null
        );

        return OAuth2TokenValidatorResult.failure(error);
    }
}
