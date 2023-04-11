package com.sigmamales.sigmafoodserver.service;

import com.sigmamales.sigmafoodserver.api.request.AddressRequest;
import com.sigmamales.sigmafoodserver.database.model.Address;
import com.sigmamales.sigmafoodserver.database.model.User;
import com.sigmamales.sigmafoodserver.database.repository.AddressRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AddressService {
    private final AddressRepository addressRepository;

    public Address getUserAddress(@NotNull User user) {
        return user.getAddress();
    }

    public Address updateUserAddress(@NotNull @Valid AddressRequest addressRequest, @NotNull User user) {
        var address = user.getAddress();
        address.updateWith(addressRequest);
        return addressRepository.save(address);
    }
}
