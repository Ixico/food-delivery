package com.sigmamales.sigmafoodserver.service;

import com.sigmamales.sigmafoodserver.api.mapper.AddressMapper;
import com.sigmamales.sigmafoodserver.api.request.ActivateAccountRequest;
import com.sigmamales.sigmafoodserver.api.request.CreateAccountRequest;
import com.sigmamales.sigmafoodserver.database.model.ActivationToken;
import com.sigmamales.sigmafoodserver.database.model.User;
import com.sigmamales.sigmafoodserver.database.repository.ActivationTokenRepository;
import com.sigmamales.sigmafoodserver.database.repository.UserRepository;
import com.sigmamales.sigmafoodserver.event.RegistrationEvent;
import com.sigmamales.sigmafoodserver.exception.InvalidActivationToken;
import com.sigmamales.sigmafoodserver.exception.UserAlreadyExistsException;
import com.sigmamales.sigmafoodserver.properties.ApplicationProperties;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
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

    private final ActivationTokenRepository activationTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final AddressMapper addressMapper;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final ValidationService validationService;

    private final ApplicationProperties applicationProperties;


    public UUID createAccount(CreateAccountRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw UserAlreadyExistsException.withEmail(request.getEmail());
        }
        validationService.validatePasswordComplexity(request.getPassword());
        var userData = request.getUserData();
        var address = addressMapper.toEntity(request.getUserData().getAddress());
        var userId = userRepository.save(
                User.builder()
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .name(userData.getName())
                        .surname(userData.getSurname())
                        .phoneNumber(userData.getPhoneNumber())
                        .address(address)
                        .enabled(false)
                        .locked(false)
                        .loginAttempts(0)
                        .lockTimestamp(null)
                        .build()
        ).getId();
        var tokenValue = activationTokenRepository.save(
                ActivationToken.builder()
                        .userId(userId)
                        .token(generateTokenValue())
                        .expiration(Instant.now()
                                .plus(applicationProperties.getActivationTokenExpirationMinutes(), ChronoUnit.MINUTES))
                        .activationAttempts(0)
                        .build()
        ).getToken();
        applicationEventPublisher.publishEvent(
                RegistrationEvent.builder()
                        .email(request.getEmail())
                        .token(tokenValue)
                        .build()
        );
        return userId;
    }


    public User activateAccount(ActivateAccountRequest request) {
        var activationTokenExists = activationTokenRepository.tokenExists(
                request.getAccountId(), request.getToken(), Instant.now(),
                applicationProperties.getAccountActivationMaxAttempts()
        );
        if (!activationTokenExists) {
            activationTokenRepository.findByUserId(request.getAccountId()).ifPresent(
                    ActivationToken::incrementActivationAttempts
            );
            throw InvalidActivationToken.instance();
        }
        var user = userRepository.getById(request.getAccountId());
        user.setEnabled(true);
        return userRepository.save(user);
    }

    @Scheduled(fixedDelay = 1000 * 60)
    public void accountRetention() {
        activationTokenRepository.deleteAllByExpirationBefore(Instant.now());
        userRepository.deleteAllByEnabledIsFalseAndActivationTokenIsNull();
    }

    private String generateTokenValue() {
        return RandomStringUtils.random(6, 0, 0, true, true, null, new SecureRandom());
    }
}
