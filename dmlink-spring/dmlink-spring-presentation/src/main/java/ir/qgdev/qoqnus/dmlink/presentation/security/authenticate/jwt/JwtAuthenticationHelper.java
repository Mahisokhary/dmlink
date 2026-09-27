package ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.jwt;

import ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.AuthenticationHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Provides methods to help with JWT authentication
 */
@Component
public class JwtAuthenticationHelper implements AuthenticationHelper {
    @Override
    public @Nullable Authentication convert(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            return JwtAuthentication.fromJwt(jwt);
        }
        return null;
    }
}
