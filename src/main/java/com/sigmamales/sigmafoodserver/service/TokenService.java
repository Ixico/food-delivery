package com.sigmamales.sigmafoodserver.service;

import com.sigmamales.sigmafoodserver.api.dto.TokenDto;
import com.sigmamales.sigmafoodserver.authentication.PrincipalContext;
import com.sigmamales.sigmafoodserver.properties.AuthenticationProperties;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithms;
import org.springframework.security.oauth2.jwt.JwsHeader;
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

    @Qualifier("refreshTokenEncoder")
    private final JwtEncoder refreshTokenEncoder;

    @Qualifier("accessTokenEncoder")
    private final JwtEncoder accessTokenEncoder;

    private final AuthenticationProperties properties;

    public TokenDto createTokens() {
        var instantNow = Instant.now();
        var refreshTokenExpiration = instantNow.plus(properties.getRefreshTokenExpirationHours(), ChronoUnit.HOURS);
        var accessTokenExpiration = instantNow.plus(properties.getAccessTokenExpirationMinutes(), ChronoUnit.HOURS);
        return TokenDto.builder()
                .refreshToken(refreshTokenEncoder.encode(buildParameters(instantNow, refreshTokenExpiration)).getTokenValue())
                .accessToken(accessTokenEncoder.encode(buildParameters(instantNow, accessTokenExpiration)).getTokenValue())
                .build();
    }

    public JwtEncoderParameters buildParameters(Instant issuedAt, Instant expiresAt) {
        var claims = JwtClaimsSet.builder()
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .subject(PrincipalContext.getCurrentUserId().toString())
                .build();
        return JwtEncoderParameters.from(JwsHeader.with(() -> JwsAlgorithms.HS256).build(), claims);
    }
}
