package com.sigmamales.sigmafoodserver.database.repository;

import com.sigmamales.sigmafoodserver.database.model.Product;
import com.sigmamales.sigmafoodserver.database.repository.common.CommonRepository;
import com.sigmamales.sigmafoodserver.exception.ProductNotFoundException;
import com.sigmamales.sigmafoodserver.exception.common.ApiException;

import java.util.UUID;

public interface ProductRepository extends CommonRepository<Product> {

    @Override
    default ApiException onNotFound(UUID id) {
        return ProductNotFoundException.withId(id);
    }
}
