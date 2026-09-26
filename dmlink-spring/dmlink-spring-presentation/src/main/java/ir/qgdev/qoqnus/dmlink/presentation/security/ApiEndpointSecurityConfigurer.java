package ir.qgdev.qoqnus.dmlink.presentation.security;

import ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.jwt.JwtSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

/**
 * Security Configurer to be used for endpoints matching {@code /api/**}
 */
public class ApiEndpointSecurityConfigurer extends AbstractHttpConfigurer<ApiEndpointSecurityConfigurer, HttpSecurity> {
    @Override
    public void init(HttpSecurity http) {
        http
                .with(new RestSecurityConfigurer())
                .with(new JwtSecurityConfigurer());
    }
}
