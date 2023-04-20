package com.sigmamales.sigmafoodserver.service;

import com.sigmamales.sigmafoodserver.exception.LowPasswordComplexityException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl._42.password.validation.PasswordValidationFailedException;
import nl._42.password.validation.PasswordValidator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ValidationService {

    private final PasswordValidator passwordValidator;

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    public void validatePasswordComplexity(@NotBlank String password) {
        try {
            passwordValidator.validate(password, null);
        } catch (PasswordValidationFailedException exception) {
            throw LowPasswordComplexityException.withReason(exception.getMessage());
        }
    }

}
