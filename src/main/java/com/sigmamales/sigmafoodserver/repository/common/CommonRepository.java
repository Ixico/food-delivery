package com.sigmamales.sigmafoodserver.repository.common;

import com.sigmamales.sigmafoodserver.exception.ResourceNotFoundException;
import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

import java.util.UUID;

@NoRepositoryBean
public interface CommonRepository<T> extends JpaRepository<T, UUID> {

    @NonNull
    default T getById(@NonNull UUID uuid) {
        return findById(uuid).orElseThrow(this::onNotFound);
    }

    default ApiException onNotFound() {
        return ResourceNotFoundException.instance();
    }

}