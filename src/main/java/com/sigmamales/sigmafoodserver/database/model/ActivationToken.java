package com.sigmamales.sigmafoodserver.database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivationToken {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private UUID userId;

    @NotBlank
    private String token;

    @NotNull
    private Instant expiration;

}
