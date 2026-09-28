package ir.qgdev.qoqnus.dmlink.presentation.security.authenticate;

import ir.qgdev.qoqnus.dmlink.domain.role.RoleRepository;
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
 * Provides session authorities
 */
@RequiredArgsConstructor
@Configuration
public class SessionAuthoritiesServiceConfiguration {
    private final Collection<? extends GrantedAuthority> devSessionAuthorities = Set.of(
    );

    private final RoleRepository roleRepository;

    @Bean
    public SessionAuthoritiesService defaultSessionAuthoritiesService() {
        return (s) -> s.getRole() != null
                ? Set.of(new RoleGrantedAuthority(s.getRole()))
                : roleRepository.getDefaultRole()
                .map(RoleGrantedAuthority::new)
                .map(Set::of)
                .orElseGet(Set::of);
    }

    @Bean
    @Profile("dev")
    @Primary
    public SessionAuthoritiesService devSessionAuthoritiesService() {
        return (s) -> devSessionAuthorities;
    }

}
