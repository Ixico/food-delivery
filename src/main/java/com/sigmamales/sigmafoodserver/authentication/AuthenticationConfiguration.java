package com.sigmamales.sigmafoodserver.authentication;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.sigmamales.sigmafoodserver.api.controller.AccountController;
import com.sigmamales.sigmafoodserver.api.controller.TokenController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfiguration {

    private static final String JWT_SIGNING_ALGORITHM = "HmacSHA256";

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChainRefreshTokenAndBasicAuth(
            HttpSecurity http, @Qualifier("refreshTokenDecoder") JwtDecoder jwtDecoder) throws Exception {
        http
                .securityMatcher(TokenController.BASE_PATH)
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(customizer -> customizer
                        .jwt()
                        .decoder(jwtDecoder)
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChainAccessToken(
            HttpSecurity http, @Qualifier("accessTokenDecoder") JwtDecoder jwtDecoder) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(AccountController.BASE_PATH).permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(customizer -> customizer
                        .jwt()
                        .decoder(jwtDecoder)
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
