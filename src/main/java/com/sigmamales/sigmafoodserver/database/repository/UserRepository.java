package com.sigmamales.sigmafoodserver.database.repository;

import com.sigmamales.sigmafoodserver.database.model.User;
import com.sigmamales.sigmafoodserver.database.repository.common.CommonRepository;
import com.sigmamales.sigmafoodserver.exception.UserNotFoundException;
import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Repository;

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

}
