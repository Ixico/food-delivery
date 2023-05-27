package com.sigmamales.sigmafoodserver.authentication;

import com.sigmamales.sigmafoodserver.database.model.User;
import com.sigmamales.sigmafoodserver.database.repository.UserRepository;
import com.sigmamales.sigmafoodserver.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountLockingManager {

    private static final Integer MAX_LOGIN_ATTEMPTS = 10;

    private static final Integer LOCK_TIME_HOURS = 1;

    private final UserRepository userRepository;

    @EventListener
    public void onFailedAuthentication(AuthenticationFailureBadCredentialsEvent event) {
        var user = getUserFromEvent(event);
        if (user == null) {
            return;
        }
        var loginAttempts = user.incrementAndGetLoginAttempts();
        log.debug("user with id {} authentication failed, login attempts: {}", user.getId(), loginAttempts);
        if (loginAttempts.equals(MAX_LOGIN_ATTEMPTS)) {
            log.info("Locking account for user with id {}", user.getId());
            user.setLocked(true);
            user.setLockTimestamp(Instant.now());
        }
        userRepository.save(user);
    }

    @EventListener
    public void onSuccessAuthentication(AuthenticationSuccessEvent event) {
        var user = getUserFromEvent(event);
        if (user == null) {
            return;
        }
        user.setLoginAttempts(0);
        userRepository.save(user);
    }

    public User getUserFromEvent(AbstractAuthenticationEvent event) {
        var email = event.getAuthentication().getPrincipal();
        if (email instanceof String) {
            return userRepository.findByEmail((String) email).orElse(null);
        }
        return null;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 2)
    public void unlockUsers() {
        log.info("Running unlock users...");
        var users = userRepository.findAllByLockedAndLockTimestampBefore(
                true, Instant.now().minus(LOCK_TIME_HOURS, ChronoUnit.MINUTES)
        );
        users.forEach(user -> {
            user.setLoginAttempts(0);
            user.setLocked(false);
            user.setLockTimestamp(null);
            log.debug("unlocking user with id {}", user.getId());
        });
        userRepository.saveAll(users);
    }

}
