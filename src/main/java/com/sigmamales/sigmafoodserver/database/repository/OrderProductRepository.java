package com.sigmamales.sigmafoodserver.database.repository;

import com.sigmamales.sigmafoodserver.database.model.OrderProduct;
import com.sigmamales.sigmafoodserver.database.repository.common.CommonRepository;
import com.sigmamales.sigmafoodserver.exception.OrderProductNotFoundException;
import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderProductRepository extends CommonRepository<OrderProduct> {
    @Override
    default ApiException onNotFound(UUID id) {
        return OrderProductNotFoundException.withId(id);
    }
}
