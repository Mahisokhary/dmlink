package ir.qgdev.qoqnus.dmlink.presentation.session;

import ir.qgdev.qoqnus.dmlink.presentation.security.ApiEndpointSecurityConfigurer;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SessionSecurityConfiguration {
    @Bean
    @Order(0)
    public SecurityFilterChain sessionSecurityFilterChain(HttpSecurity http) {
        return http
                .with(new ApiEndpointSecurityConfigurer())
                .securityMatcher("/api/session/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/session/").hasAnyRole("SESSION_CREATE")
                        .requestMatchers(HttpMethod.GET, "/api/session/").hasAnyRole("SESSION_GET_ALL")
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .anyRequest().denyAll()
                )
                .build();
    }
}
