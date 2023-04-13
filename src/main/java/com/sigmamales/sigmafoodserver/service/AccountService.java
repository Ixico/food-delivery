package com.sigmamales.sigmafoodserver.service;

import com.sigmamales.sigmafoodserver.api.mapper.AddressMapper;
import com.sigmamales.sigmafoodserver.api.request.ActivateAccountRequest;
import com.sigmamales.sigmafoodserver.api.request.CreateAccountRequest;
import com.sigmamales.sigmafoodserver.database.model.ActivationToken;
import com.sigmamales.sigmafoodserver.database.model.User;
import com.sigmamales.sigmafoodserver.database.repository.UserRepository;
import com.sigmamales.sigmafoodserver.exception.UserAlreadyExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AddressMapper addressMapper;


    public UUID createAccount(CreateAccountRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw UserAlreadyExistsException.withEmail(request.getEmail());
        }
        var userData = request.getUserData();
        var address = addressMapper.toEntity(request.getUserData().getAddress());
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(userData.getName())
                .surname(userData.getSurname())
                .phoneNumber(userData.getPhoneNumber())
                .address(address)
                .enabled(false)
                .build();
        var activationToken = ActivationToken.builder()
                .user(user)
                .token(generateTokenValue())
                .expiration(Instant.now().plus(10, ChronoUnit.MINUTES))
                .build();
        user.setActivationToken(activationToken);
        return userRepository.save(user).getId();
    }


    // TODO: security issues
    public User activateAccount(ActivateAccountRequest request) {
        var user = userRepository.getById(request.getAccountId());
        var activationToken = user.getActivationToken();
        if (activationToken.getToken().equals(request.getToken())) {
            user.setEnabled(true);
        }
        return null;
    }

    private String generateTokenValue() {
        return RandomStringUtils.random(6, 0, 0, true, true, null, new SecureRandom());
    }
}
