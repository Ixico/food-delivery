package com.sigmamales.sigmafoodserver.database.repository;

import com.sigmamales.sigmafoodserver.database.model.ActivationToken;
import com.sigmamales.sigmafoodserver.database.repository.common.CommonRepository;
import com.sigmamales.sigmafoodserver.exception.ActivationTokenNotFoundException;
import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

@Repository
public interface ActivationTokenRepository extends CommonRepository<ActivationToken> {

    @Override
    default ApiException onNotFound(UUID id) {
        return ActivationTokenNotFoundException.withId(id);
    }

    boolean existsByUserIdAndTokenAndExpirationAfter(@NotNull UUID userId, @NotBlank String token, @NotNull Instant expirationAfter);

}
