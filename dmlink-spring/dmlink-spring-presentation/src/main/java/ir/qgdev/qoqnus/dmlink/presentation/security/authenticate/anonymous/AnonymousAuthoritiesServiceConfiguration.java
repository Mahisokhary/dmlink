package ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.anonymous;

import ir.qgdev.qoqnus.dmlink.domain.role.RoleRepository;
import ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.RoleGrantedAuthority;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;

/**
 * Provides anonymous user authorities
 */
@RequiredArgsConstructor
@Configuration
public class AnonymousAuthoritiesServiceConfiguration {
    private final Collection<? extends GrantedAuthority> devAnonymousAuthorities = Set.of(
            new SimpleGrantedAuthority("ROLE_LINK_GET_NAME"),
            new SimpleGrantedAuthority("ROLE_USER_CREATE"),
            new SimpleGrantedAuthority("ROLE_SESSION_CREATE")
    );

    private final RoleRepository roleRepository;

    @Bean
    public AnonymousAuthoritiesService defaultAnonymousAuthoritiesService() {
        return () -> roleRepository.getAnonymousRole()
                .map(RoleGrantedAuthority::new)
                .map(Set::of)
                .orElseGet(Set::of);
    }

    @Bean
    @Profile("dev")
    @Primary
    public AnonymousAuthoritiesService devAnonymousAuthoritiesService() {
        return () -> devAnonymousAuthorities;
    }

}
