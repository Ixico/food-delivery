package com.sigmamales.sigmafoodserver.service;

import com.sigmamales.sigmafoodserver.authentication.PrincipalContext;
import com.sigmamales.sigmafoodserver.properties.AuthenticationProperties;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TokenService {

    private final JwtEncoder jwtEncoder;

    private final AuthenticationProperties authenticationProperties;

    public String createToken() {
        var currentUserId = PrincipalContext.getCurrentUserId().toString();
        log.debug("Issuing token for user with id {}", currentUserId);
        var instantNow = Instant.now();
        var claims = JwtClaimsSet.builder()
                .issuedAt(instantNow)
                .expiresAt(instantNow.plus(authenticationProperties.getTokenExpirationTimeHours(), ChronoUnit.HOURS))
                .subject(currentUserId)
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
