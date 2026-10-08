package ir.qgdev.qoqnus.dmlink.presentation.session;

import ir.qgdev.qoqnus.dmlink.presentation.security.ApiSecurityConfigurer;
import ir.qgdev.qoqnus.dmlink.presentation.security.authorize.AuthorizationManagerFactory;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class SessionSecurityConfiguration {
    private final AuthorizationManagerFactory authorize;

    @Bean
    @Order(0)
    public SecurityFilterChain sessionSecurityFilterChain(HttpSecurity http) {
        return http
                .with(new ApiSecurityConfigurer())
                .securityMatcher("/session/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/session/@me").access(authorize.hasAction(SessionActions.GET_SELF))
                        .requestMatchers(HttpMethod.DELETE, "/session/@me").access(authorize.hasAction(SessionActions.DELETE_SELF))
                        .requestMatchers(HttpMethod.POST, "/session/").access(authorize.hasAction(SessionActions.CREATE))
                        .requestMatchers(HttpMethod.GET, "/session/").access(authorize.hasAction(SessionActions.GET_ALL))
                        .requestMatchers(HttpMethod.GET, "/session/*").access(authorize.hasAction(SessionActions.GET))
                        .requestMatchers(HttpMethod.DELETE, "/session/*").access(authorize.hasAction(SessionActions.DELETE))
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .anyRequest().denyAll()
                )
                .build();
    }
}
