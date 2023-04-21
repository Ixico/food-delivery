package com.sigmamales.sigmafoodserver.authentication;

import com.sigmamales.sigmafoodserver.controller.AccountController;
import com.sigmamales.sigmafoodserver.controller.TokenController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@ComponentScan("nl._42.password.validation")
public class AuthenticationConfiguration {

    private final RevokedTokenFilter revokedTokenFilter;

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChainRefreshTokenAndBasicAuth(
            HttpSecurity http, @Qualifier("refreshTokenDecoder") JwtDecoder jwtDecoder) throws Exception {
        http
                .securityMatcher(TokenController.BASE_PATH)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.DELETE).permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(customizer -> customizer
                        .jwt()
                        .decoder(jwtDecoder)
                )
                .addFilterBefore(revokedTokenFilter, BearerTokenAuthenticationFilter.class)
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


}
