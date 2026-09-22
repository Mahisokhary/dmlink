package ir.qgdev.qoqnus.dmlink.presentation.security.authenticate;

import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import ir.qgdev.qoqnus.dmlink.domain.session.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * Provides authentication via JWTs
 */
@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtService jwtService;
    private final SessionRepository sessionRepository;

    @Override
    public @Nullable Authentication authenticate(@NonNull Authentication authentication) throws AuthenticationException {
        if (authentication instanceof JwtAuthentication token) {
            try {
                String credentials = token.getCredentials();
                Long sessionId = Long.parseLong(jwtService.verifyAndFetchId(credentials));
                Session session = sessionRepository.findById(sessionId).orElseThrow();
                return JwtAuthentication.fromSession(session);
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
