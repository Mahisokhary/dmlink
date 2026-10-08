package ir.qgdev.qoqnus.dmlink.presentation.security;

import ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.jwt.JwtSecurityConfigurer;
import ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.anonymous.AnonymousConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

/**
 * Security Configurer to be used for RESTful API endpoints
 */
public class ApiSecurityConfigurer extends AbstractHttpConfigurer<ApiSecurityConfigurer, HttpSecurity> {
    @Override
    public void init(HttpSecurity http) {
        http
                .with(new RestSecurityConfigurer())
                .with(new AnonymousConfigurer())
                .with(new JwtSecurityConfigurer());
    }
}
