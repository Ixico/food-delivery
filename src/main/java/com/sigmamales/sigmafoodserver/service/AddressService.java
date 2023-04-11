package com.sigmamales.sigmafoodserver.service;

import com.sigmamales.sigmafoodserver.database.model.Address;
import com.sigmamales.sigmafoodserver.database.model.User;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AddressService {

    public Address getUserAddress(@NotNull User user) {
        return user.getAddress();
    }

}
