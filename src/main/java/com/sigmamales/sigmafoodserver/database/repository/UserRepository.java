package com.sigmamales.sigmafoodserver.database.repository;

import com.sigmamales.sigmafoodserver.database.model.User;
import com.sigmamales.sigmafoodserver.database.repository.common.CommonRepository;
import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import com.sigmamales.sigmafoodserver.exception.notfound.UserNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CommonRepository<User> {

    @Override
    default ApiException onNotFound(UUID id) {
        return UserNotFoundException.withId(id);
    }

    Optional<User> findByEmail(@NotBlank String email);

    boolean existsByEmail(@NotBlank String email);

    void deleteAllByEnabledIsFalseAndActivationTokenIsNull();

    List<User> findAllByLockedAndLockTimestampBefore(@NotNull Boolean locked, @NotNull Instant before);

}
