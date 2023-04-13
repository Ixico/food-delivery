package com.sigmamales.sigmafoodserver.api.controller;

import com.sigmamales.sigmafoodserver.api.dto.AddressDto;
import com.sigmamales.sigmafoodserver.api.dto.UserDto;
import com.sigmamales.sigmafoodserver.api.mapper.AddressMapper;
import com.sigmamales.sigmafoodserver.api.mapper.UserMapper;
import com.sigmamales.sigmafoodserver.api.request.UserRequest;
import com.sigmamales.sigmafoodserver.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(UserController.BASE_PATH)
@Transactional
public class UserController {

    public static final String BASE_PATH = "/user";

    private final AddressMapper addressMapper;

    private final UserMapper userMapper;

    private final UserService userService;


    @GetMapping("/address")
    public AddressDto getUserAddress() {
        return addressMapper.toDto(userService.getUserAddress());
    }

    @GetMapping
    public UserDto getUserData() {
        return userMapper.toDto(userService.getCurrentUser());
    }

    @PutMapping
    public UserDto updateUserData(@NotNull @Valid @RequestBody UserRequest userRequest) {
        return userMapper.toDto(userService.updateUserData(userRequest));
    }
}
