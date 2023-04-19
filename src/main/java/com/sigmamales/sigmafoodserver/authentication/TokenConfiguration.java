package com.sigmamales.sigmafoodserver.authentication;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

@Configuration
@RequiredArgsConstructor
public class TokenConfiguration {

    private static final String JWT_SIGNING_ALGORITHM = "HmacSHA256";

    @Bean("refreshTokenDecoder")
    public JwtDecoder refreshTokenDecoder(@Qualifier("refreshTokenSecret") SecretKey jwtSecret) {
        return NimbusJwtDecoder.withSecretKey(jwtSecret).build();
    }

    @Bean("refreshTokenEncoder")
    public JwtEncoder refreshTokenEncoder(@Qualifier("refreshTokenSecret") SecretKey jwtSecret) {
        return new NimbusJwtEncoder(new ImmutableSecret<>(jwtSecret));
    }

    @Bean("refreshTokenSecret")
    public SecretKey refreshTokenSecret() throws NoSuchAlgorithmException {
        return KeyGenerator.getInstance(JWT_SIGNING_ALGORITHM).generateKey();
    }

    @Bean("accessTokenDecoder")
    public JwtDecoder accessTokenDecoder(@Qualifier("accessTokenSecret") SecretKey jwtSecret) {
        return NimbusJwtDecoder.withSecretKey(jwtSecret).build();
    }

    @Bean("accessTokenEncoder")
    public JwtEncoder accessTokenEncoder(@Qualifier("accessTokenSecret") SecretKey jwtSecret) {
        return new NimbusJwtEncoder(new ImmutableSecret<>(jwtSecret));
    }

    @Bean("accessTokenSecret")
    public SecretKey accessTokenSecret() throws NoSuchAlgorithmException {
        return KeyGenerator.getInstance(JWT_SIGNING_ALGORITHM).generateKey();
    }

}
