package ir.qgdev.qoqnus.dmlink.presentation.action;

import ir.qgdev.qoqnus.dmlink.presentation.security.ApiSecurityConfigurer;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class ActionSecurityConfiguration {
    @Bean
    @Order(0)
    public SecurityFilterChain actionSecurityFilterChain(HttpSecurity http) {
        return http
                .with(new ApiSecurityConfigurer())
                .securityMatcher("/action/**")
                .authorizeHttpRequests(auth->auth
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .anyRequest().denyAll()
                )
                .build();
    }

}
