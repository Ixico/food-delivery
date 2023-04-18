package com.sigmamales.sigmafoodserver.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Data
@Configuration
@ConfigurationProperties(prefix = "authentication")
@Validated
public class AuthenticationProperties {

    @NotNull
    private RSAPublicKey publicKey;

    @NotNull
    private RSAPrivateKey privateKey;

    @NotNull
    private Integer accessTokenExpirationMinutes;

    @NotNull
    private Integer refreshTokenExpirationHours;
}
