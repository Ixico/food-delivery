package com.sigmamales.sigmafoodserver.service;

import com.sigmamales.sigmafoodserver.api.dto.TokenDto;
import com.sigmamales.sigmafoodserver.authentication.PrincipalContext;
import com.sigmamales.sigmafoodserver.properties.ApplicationProperties;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TokenService {


    private final Set<String> revokedTokens = Collections.synchronizedSet(new HashSet<>());

    @Qualifier("refreshTokenEncoder")
    private final JwtEncoder refreshTokenEncoder;

    @Qualifier("accessTokenEncoder")
    private final JwtEncoder accessTokenEncoder;

    private final ApplicationProperties properties;


    public TokenDto createTokens() {
        var instantNow = Instant.now();
        var refreshTokenExpiration = instantNow.plus(properties.getRefreshTokenExpirationHours(), ChronoUnit.HOURS);
        var accessTokenExpiration = instantNow.plus(properties.getAccessTokenExpirationMinutes(), ChronoUnit.HOURS);
        var tokens = TokenDto.builder()
                .refreshToken(refreshTokenEncoder.encode(buildParameters(instantNow, refreshTokenExpiration)).getTokenValue())
                .accessToken(accessTokenEncoder.encode(buildParameters(instantNow, accessTokenExpiration)).getTokenValue())
                .build();
        PrincipalContext.getCurrentTokenValue().ifPresent(this::revokeToken);
        return tokens;
    }

    public JwtEncoderParameters buildParameters(Instant issuedAt, Instant expiresAt) {
        var claims = JwtClaimsSet.builder()
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .subject(PrincipalContext.getCurrentUserId().toString())
                .build();
        return JwtEncoderParameters.from(JwsHeader.with(() -> JwsAlgorithms.HS256).build(), claims);
    }


    public boolean isRevoked(@NotNull String token) {
        return revokedTokens.contains(token);
    }

    public void revokeToken(String token) {
        revokedTokens.add(token);
    }

}
