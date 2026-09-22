package ir.qgdev.qoqnus.dmlink.presentation.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Security Configurer for RESTful APIs
 */
public class RestSecurityConfigurer extends AbstractHttpConfigurer<RestSecurityConfigurer, HttpSecurity> {
    @Override
    public void init(HttpSecurity http) {
        http
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable);
    }
}
