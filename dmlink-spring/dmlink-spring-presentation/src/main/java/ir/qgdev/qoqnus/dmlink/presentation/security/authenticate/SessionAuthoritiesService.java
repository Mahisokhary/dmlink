package ir.qgdev.qoqnus.dmlink.presentation.security.authenticate;

import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Provide authorities of a session
 */
@FunctionalInterface
public interface SessionAuthoritiesService {
    /**
     * Fetches authorities of given session
     */
    Collection<? extends GrantedAuthority> fetchAuthorities(Session session);

}
