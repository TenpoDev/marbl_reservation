package com.marbl.reservation.config;

import com.marbl.reservation.token.Token;
import com.marbl.reservation.token.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRefreshFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final UserDetailsService userDetailsService;

    private static final long ONE_HOUR_IN_MILLIS = 60L * 60 * 1000; // 1 hour in milliseconds

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {
        log.info("Attempting to refresh Jwt Token");

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String accessToken;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }

        accessToken = authHeader.substring(7);

        tokenRepository.findByToken(accessToken).ifPresentOrElse(
                token -> {
                    if (isTokenRefreshable(token)) {
                        refreshAccessToken(token, request);

                    }
                },
                () -> log.warn("Token not found for access token: {}", accessToken)
        );
        filterChain.doFilter(request,response);
    }

    private boolean isTokenRefreshable(Token token) {
        return !token.isExpired() && !token.isRevoked() && isTokenRefreshable(token.getToken());
    }

    private boolean isTokenRefreshable(String token) {
        Date expirationTime = jwtService.extractExpirationDate(token);

        Date currentTime = new Date();

        long oneHourInMillis = 60L * 60 * 1000; // 1 hour in milliseconds
        Date oneHourBeforeExpiration = new Date(expirationTime.getTime() - oneHourInMillis);

        return currentTime.after(oneHourBeforeExpiration) && currentTime.before(expirationTime);
    }



    private void refreshAccessToken(Token token, @NotNull HttpServletRequest request) {
        String newRefreshToken = jwtService.generateRefreshToken(token.getUser());
        token.setToken(newRefreshToken);
        tokenRepository.save(token);
        request.setAttribute(token.getToken(), HttpHeaders.AUTHORIZATION);
    }

}
