package com.sigmamales.sigmafoodserver.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Configuration
@ConfigurationProperties(prefix = "application-properties")
@Validated
public class ApplicationProperties {

    @NotNull
    private Integer accessTokenExpirationMinutes;

    @NotNull
    private Integer refreshTokenExpirationHours;
}
