package ir.qgdev.qoqnus.dmlink.presentation.role;

import ir.qgdev.qoqnus.dmlink.presentation.security.ApiEndpointSecurityConfigurer;
import ir.qgdev.qoqnus.dmlink.presentation.security.authorize.AuthorizationManagerFactory;
import ir.qgdev.qoqnus.dmlink.presentation.security.authorize.QueryParamRequestMatcher;
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
public class RoleSecurityConfiguration {
    private final AuthorizationManagerFactory authorize;

    @Bean
    @Order(0)
    public SecurityFilterChain roleSecurityFilterChain(HttpSecurity http) {
        return http
                .with(new ApiEndpointSecurityConfigurer())
                .securityMatcher("/api/role/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new QueryParamRequestMatcher(HttpMethod.GET, "/api/role/", "name")).access(authorize.hasAction(RoleActions.GET_BY_NAME))
                        .requestMatchers(HttpMethod.POST, "/api/role/").access(authorize.hasAction(RoleActions.CREATE))
                        .requestMatchers(HttpMethod.GET, "/api/role/").access(authorize.hasAction(RoleActions.GET_ALL))
                        .requestMatchers(HttpMethod.GET, "/api/role/*").access(authorize.hasAction(RoleActions.GET))
                        .requestMatchers(HttpMethod.PUT, "/api/role/*").access(authorize.hasAction(RoleActions.MODIFY))
                        .requestMatchers(HttpMethod.DELETE, "/api/role/*").access(authorize.hasAction(RoleActions.DELETE))
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .anyRequest().denyAll()
                )
                .build();
    }
}
