package ir.qgdev.qoqnus.dmlink.presentation.link;

import ir.qgdev.qoqnus.dmlink.presentation.security.ApiEndpointSecurityConfigurer;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class LinkSecurityConfiguration {
    @Bean
    @Order(0)
    public SecurityFilterChain linkSecurityFilterChain(HttpSecurity http) {
        return http
                .with(new ApiEndpointSecurityConfigurer())
                .securityMatcher("/api/link/**")
                .authorizeHttpRequests(auth -> auth
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .anyRequest().denyAll()
                )
                .build();
    }

}
