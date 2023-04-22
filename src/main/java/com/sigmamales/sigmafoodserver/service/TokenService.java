package com.sigmamales.sigmafoodserver.service;

import com.sigmamales.sigmafoodserver.api.dto.TokenDto;
import com.sigmamales.sigmafoodserver.authentication.PrincipalContext;
import com.sigmamales.sigmafoodserver.exception.InvalidJwtException;
import com.sigmamales.sigmafoodserver.exception.TokenAlreadyRevokedException;
import com.sigmamales.sigmafoodserver.exception.TokenJustCreatedException;
import com.sigmamales.sigmafoodserver.properties.ApplicationProperties;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithms;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
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

    @Qualifier("refreshTokenDecoder")
    private final JwtDecoder refreshTokenDecoder;

    private final ApplicationProperties properties;


    public TokenDto createTokens() {
        var currentToken = PrincipalContext.getCurrentToken();
        currentToken.map(jwt -> jwt.getClaimAsInstant("iat")).ifPresent(issued -> {
            if (Duration.between(issued, Instant.now()).toMinutes() < 1) {
                throw TokenJustCreatedException.instance();
            }
        });
        var instantNow = Instant.now();
        var refreshTokenExpiration = instantNow.plus(properties.getRefreshTokenExpirationHours(), ChronoUnit.HOURS);
        var accessTokenExpiration = instantNow.plus(properties.getAccessTokenExpirationMinutes(), ChronoUnit.MINUTES);
        var tokens = TokenDto.builder()
                .refreshToken(refreshTokenEncoder.encode(buildParameters(instantNow, refreshTokenExpiration)).getTokenValue())
                .accessToken(accessTokenEncoder.encode(buildParameters(instantNow, accessTokenExpiration)).getTokenValue())
                .build();
        currentToken.map(Jwt::getTokenValue).ifPresent(this::revokeToken);
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
        assertRefreshTokenValid(token);
        if (revokedTokens.contains(token)) {
            throw TokenAlreadyRevokedException.instance();
        }
        revokedTokens.add(token);
    }

    public void assertRefreshTokenValid(@NotBlank String token) {
        try {
            refreshTokenDecoder.decode(token);
        } catch (Exception ignore) {
            throw InvalidJwtException.instance();
        }
    }

    @Scheduled(fixedDelay = 1000 * 60)
    public void tokenRetention() {
        log.info("Running token retention...");
        var toDelete = new HashSet<String>();
        revokedTokens.forEach(token -> {
            try {
                var jwt = refreshTokenDecoder.decode(token);
                if (jwt.getClaimAsInstant("exp").isBefore(Instant.now())) {
                    toDelete.add(token);
                    log.debug("Removing token {}", token);
                }
            } catch (Exception ex) {
                log.error("Exception on decoding token", ex);
                toDelete.add(token);
            }
        });
        revokedTokens.removeAll(toDelete);
    }
}
