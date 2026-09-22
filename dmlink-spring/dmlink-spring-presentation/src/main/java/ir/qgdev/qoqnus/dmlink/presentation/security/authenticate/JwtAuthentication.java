package ir.qgdev.qoqnus.dmlink.presentation.security.authenticate;

import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.Collections;

/**
 * Implementation of {@link Authentication} for JWTs
 */
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public final class JwtAuthentication implements Authentication {
    private boolean authenticated;
    private final Session principal;
    private final Collection<RoleGrantedAuthority> authorities;
    private final String credentials;

    public static JwtAuthentication fromJwt(String jwt) {
        return new JwtAuthentication(false, null, null, jwt);
    }

    public static JwtAuthentication fromSession(Session  session) {
        Collection<RoleGrantedAuthority> authorities = session.getRole() == null
                ? Collections.emptySet()
                : Collections.singleton(new RoleGrantedAuthority(session.getRole()));
        return new JwtAuthentication(true, session, authorities, null);
    }

    @Override
    public @Nullable Object getDetails() {
        return null;
    }

    @Override
    public String getName() {
        return principal.getId().toString();
    }

    public void setAuthenticated(boolean authenticated) {
        if (authenticated) throw new IllegalArgumentException("authenticated cant be set to true");
        this.authenticated = false;
    }
}
