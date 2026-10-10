package ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.anonymous;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Transient;

import java.util.Collection;

/**
 * Authentication information for anonymous users
 */
@Transient
public record AnonymousAuthentication(Collection<? extends GrantedAuthority> getAuthorities) implements Authentication {
    @Override
    public @Nullable Object getCredentials() {
        return null;
    }

    @Override
    public @Nullable Object getDetails() {
        return null;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) throw new IllegalArgumentException("authenticated cant be set to true");
    }

    @Override
    public String getName() {
        return null;
    }
}
