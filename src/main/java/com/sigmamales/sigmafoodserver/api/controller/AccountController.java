package com.sigmamales.sigmafoodserver.api.controller;

import com.sigmamales.sigmafoodserver.api.mapper.UserMapper;
import com.sigmamales.sigmafoodserver.api.request.CreateAccountRequest;
import com.sigmamales.sigmafoodserver.service.AccountService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(AccountController.BASE_PATH)
@Transactional
public class AccountController {

    public static final String BASE_PATH = "/account";

    private final AccountService accountService;

    private final UserMapper userMapper;

    @PostMapping
    public UUID createAccount(@NotNull @Valid @RequestBody CreateAccountRequest createAccountRequest) {
        return accountService.createAccount(createAccountRequest);
    }

//    @PutMapping
//    public UserDto activateAccount(@NotNull @Valid @RequestBody ActivateAccountRequest activateAccountRequest) {
//        return userMapper.toDto(accountService.activateAccount(activateAccountRequest));
//    }
}
