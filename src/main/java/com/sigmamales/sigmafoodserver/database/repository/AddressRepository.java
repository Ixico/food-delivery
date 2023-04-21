package com.sigmamales.sigmafoodserver.database.repository;

import com.sigmamales.sigmafoodserver.database.model.Address;
import com.sigmamales.sigmafoodserver.database.repository.common.CommonRepository;
import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import com.sigmamales.sigmafoodserver.exception.notfound.AddressNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends CommonRepository<Address> {

    @Override
    default ApiException onNotFound(UUID id) {
        return AddressNotFoundException.withId(id);
    }
}
