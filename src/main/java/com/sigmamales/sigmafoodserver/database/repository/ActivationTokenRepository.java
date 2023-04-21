package com.sigmamales.sigmafoodserver.database.repository;

import com.sigmamales.sigmafoodserver.database.model.ActivationToken;
import com.sigmamales.sigmafoodserver.database.repository.common.CommonRepository;
import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import com.sigmamales.sigmafoodserver.exception.notfound.ActivationTokenNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActivationTokenRepository extends CommonRepository<ActivationToken> {

    @Override
    default ApiException onNotFound(UUID id) {
        return ActivationTokenNotFoundException.withId(id);
    }

    boolean existsByUserIdAndTokenAndExpirationAfterAndActivationAttemptsLessThan(
            @NotNull UUID userId, @NotBlank String token,
            @NotNull Instant expirationAfter, @NotNull Integer activationAttempts
    );

    default boolean tokenExists(
            @NotNull UUID userId,
            @NotBlank String token,
            @NotNull Instant expirationAfter,
            @NotNull Integer activationAttempts
    ) {
        return existsByUserIdAndTokenAndExpirationAfterAndActivationAttemptsLessThan(
                userId, token, expirationAfter, activationAttempts
        );
    }

    void deleteAllByExpirationBefore(@NotNull Instant instant);

    Optional<ActivationToken> findByUserId(@NotNull UUID userId);
}
