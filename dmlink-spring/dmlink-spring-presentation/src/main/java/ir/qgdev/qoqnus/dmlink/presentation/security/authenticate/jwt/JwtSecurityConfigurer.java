package ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.jwt;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security Configurer for JWT authentication
 */
public class JwtSecurityConfigurer extends AbstractHttpConfigurer<JwtSecurityConfigurer, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http) {
        ApplicationContext context = http.getSharedObject(ApplicationContext.class);
        AuthenticationManager authenticationManager = context.getBean(AuthenticationManager.class);
        JwtAuthenticationHelper helper = context.getBean(JwtAuthenticationHelper.class);
        AuthenticationFilter filter = new AuthenticationFilter(authenticationManager, helper);
        filter.setSuccessHandler(helper);
        filter.setFailureHandler(helper);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
