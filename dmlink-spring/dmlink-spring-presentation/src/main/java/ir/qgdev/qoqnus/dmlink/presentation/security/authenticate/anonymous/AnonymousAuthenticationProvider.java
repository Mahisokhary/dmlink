package ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.anonymous;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AnonymousAuthenticationProvider implements AuthenticationProvider {
    @Override
    public @Nullable Authentication authenticate(@NonNull Authentication authentication) throws AuthenticationException {
        return authentication;
    }

    @Override
    public boolean supports(@NonNull Class<?> authentication) {
        return authentication == AnonymousAuthentication.class;
    }

}
