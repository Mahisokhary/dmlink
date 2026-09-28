package ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.anonymous;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Provides authorities of an anonymous user
 */
@FunctionalInterface
public interface AnonymousAuthoritiesService {
    /**
     * Returns authorities of an anonymous user
     */
    Collection<? extends GrantedAuthority> getAuthorities();

}
