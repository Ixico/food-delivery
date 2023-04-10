package com.sigmamales.sigmafoodserver.database.repository.common;

import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

import java.util.UUID;

@NoRepositoryBean
public interface CommonRepository<T> extends JpaRepository<T, UUID> {

    @NonNull
    default T getById(@NonNull UUID uuid) {
        return findById(uuid).orElseThrow(() -> onNotFound(uuid));
    }

    ApiException onNotFound(UUID uuid);

}
