package com.sigmamales.sigmafoodserver.repository;

import com.sigmamales.sigmafoodserver.exception.UserNotFoundException;
import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import com.sigmamales.sigmafoodserver.model.User;
import com.sigmamales.sigmafoodserver.repository.common.CommonRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CommonRepository<User> {

    @Override
    default ApiException onNotFound() {
        return UserNotFoundException.instance();
    }

    Optional<User> findByEmail(@NotBlank String email);

}
