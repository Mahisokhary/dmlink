package ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.anonymous;

import ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.AuthenticationHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@RequiredArgsConstructor
@Component
public class AnonymousAuthenticationHelper implements AuthenticationHelper {
    private final AnonymousAuthoritiesService anonymousAuthoritiesService;

    @Override
    public @Nullable Authentication convert(@NonNull HttpServletRequest request) {
        Collection<? extends GrantedAuthority> authorities = anonymousAuthoritiesService.getAuthorities();
        if (!authorities.isEmpty()) {
            return new AnonymousAuthentication(authorities);
        }
        return null;
    }

}
