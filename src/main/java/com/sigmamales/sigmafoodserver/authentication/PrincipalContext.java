package com.sigmamales.sigmafoodserver.authentication;

import com.sigmamales.sigmafoodserver.database.model.User;
import com.sigmamales.sigmafoodserver.exception.UserNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public class PrincipalContext {


    public static UUID getCurrentUserId() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return ((User) principal).getId();
        }
        if (principal instanceof Jwt) {
            return UUID.fromString(((Jwt) principal).getSubject());
        }
        throw UserNotFoundException.instance();
    }

}
