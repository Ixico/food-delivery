package com.sigmamales.sigmafoodserver.authentication;

import com.sigmamales.sigmafoodserver.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RevokedTokenFilter extends OncePerRequestFilter {

    private final BearerTokenResolver bearerTokenResolver = new DefaultBearerTokenResolver();

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token;
        try {
            token = bearerTokenResolver.resolve(request);
        } catch (Exception exception) {
            logger.trace("Cannot resolve bearer token");
            filterChain.doFilter(request, response);
            return;
        }
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (tokenService.isRevoked(token)) {
            logger.debug("Bearer token has been revoked");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
