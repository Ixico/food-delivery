package com.sigmamales.sigmafoodserver.api.controller;

import com.sigmamales.sigmafoodserver.api.dto.AddressDto;
import com.sigmamales.sigmafoodserver.api.mapper.AddressMapper;
import com.sigmamales.sigmafoodserver.api.request.AddressRequest;
import com.sigmamales.sigmafoodserver.service.AddressService;
import com.sigmamales.sigmafoodserver.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AddressController.BASE_PATH)
@Transactional
public class AddressController {

    public static final String BASE_PATH = "/address";

    private final AddressService addressService;

    private final AddressMapper addressMapper;

    private final UserService userService;


    @GetMapping
    public AddressDto getUserAddress() {
        return addressMapper.toDto(addressService.getUserAddress(userService.getCurrentUser()));
    }

    @PutMapping
    public AddressDto updateUserAddress(@NotNull @Valid @RequestBody AddressRequest addressRequest) {
        return addressMapper.toDto(
                addressService.updateUserAddress(addressRequest, userService.getCurrentUser())
        );
    }
}
