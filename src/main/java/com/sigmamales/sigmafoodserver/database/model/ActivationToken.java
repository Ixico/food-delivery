package com.sigmamales.sigmafoodserver.database.model;

import jakarta.persistence.*;
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
    @Column(name = "user_id")
    private UUID userId;

    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @NotBlank
    private String token;

    @NotNull
    private Instant expiration;

    @NotNull
    private Integer activationAttempts;

    public void incrementActivationAttempts() {
        activationAttempts++;
    }

}
