package ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.jwt;

import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import ir.qgdev.qoqnus.dmlink.domain.session.SessionRepository;
import ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.SessionAuthoritiesService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Provides authentication via JWTs
 */
@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtService jwtService;
    private final SessionRepository sessionRepository;
    private final SessionAuthoritiesService authoritiesHolder;

    @Override
    public @Nullable Authentication authenticate(@NonNull Authentication authentication) throws AuthenticationException {
        if (authentication instanceof JwtAuthentication token) {
            try {
                String credentials = token.getCredentials();
                Long sessionId = Long.parseLong(jwtService.verifyAndFetchId(credentials));
                Session session = sessionRepository.findById(sessionId).orElseThrow();
                Collection<? extends GrantedAuthority> authorities = authoritiesHolder.fetchAuthorities(session);
                return JwtAuthentication.fromSession(session, authorities);
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid jwt");
            }
        }
        return null;
    }

    @Override
    public boolean supports(@NonNull Class<?> authentication) {
        return authentication.equals(JwtAuthentication.class);
    }
}
