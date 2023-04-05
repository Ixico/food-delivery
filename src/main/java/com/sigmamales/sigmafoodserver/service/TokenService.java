package com.sigmamales.sigmafoodserver.service;

import com.sigmamales.sigmafoodserver.authentication.PrincipalContext;
import com.sigmamales.sigmafoodserver.properties.AuthenticationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtEncoder jwtEncoder;

    private final AuthenticationProperties authenticationProperties;

    public String createToken() {
        var instantNow = Instant.now();
        var claims = JwtClaimsSet.builder()
                .issuedAt(instantNow)
                .expiresAt(instantNow.plus(authenticationProperties.getTokenExpirationTimeHours(), ChronoUnit.HOURS))
                .subject(PrincipalContext.getCurrentUserId().toString())
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
