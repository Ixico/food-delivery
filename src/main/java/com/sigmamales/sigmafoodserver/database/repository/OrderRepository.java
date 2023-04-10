package com.sigmamales.sigmafoodserver.database.repository;

import com.sigmamales.sigmafoodserver.database.model.Order;
import com.sigmamales.sigmafoodserver.database.model.User;
import com.sigmamales.sigmafoodserver.database.repository.common.CommonRepository;
import com.sigmamales.sigmafoodserver.exception.OrderNotFoundException;
import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends CommonRepository<Order> {

    @Override
    default ApiException onNotFound(UUID id) {
        return OrderNotFoundException.withId(id);
    }

    List<Order> findAllByUser(@NotNull User user);
}
