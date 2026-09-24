package ir.qgdev.qoqnus.dmlink.presentation.user;

import ir.qgdev.qoqnus.dmlink.presentation.security.ApiEndpointSecurityConfigurer;
import ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.JwtAuthenticationHelper;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class UserSecurityConfiguration {
    @Bean
    @Order(0)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http, JwtAuthenticationHelper jwtFilter) {
        return http
                .with(new ApiEndpointSecurityConfigurer())
                .securityMatcher("/api/user/**")
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(HttpMethod.POST, "/api/user/").hasAnyRole("USER_CREATE")
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .anyRequest().denyAll()
                )
                .build();
    }

}
