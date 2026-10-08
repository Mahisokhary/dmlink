package ir.qgdev.qoqnus.dmlink.presentation.user;

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
public class UserSecurityConfiguration {
    private final AuthorizationManagerFactory authorize;

    @Bean
    @Order(0)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) {
        return http
                .with(new ApiEndpointSecurityConfigurer())
                .securityMatcher("/user/**")
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(new QueryParamRequestMatcher(HttpMethod.GET, "/user/", "username")).access(authorize.hasAction(UserActions.GET_BY_USERNAME))
                        .requestMatchers(HttpMethod.GET, "/user/@me").access(authorize.hasAction(UserActions.GET_SELF))
                        .requestMatchers(HttpMethod.DELETE, "/user/@me").access(authorize.hasAction(UserActions.DELETE_SELF))
                        .requestMatchers(HttpMethod.PUT, "/user/@me/password").access(authorize.hasAction(UserActions.MODIFY_SELF_PASSWORD))
                        .requestMatchers(HttpMethod.POST, "/user/").access(authorize.hasAction(UserActions.CREATE))
                        .requestMatchers(HttpMethod.GET, "/user/").access(authorize.hasAction(UserActions.GET_ALL))
                        .requestMatchers(HttpMethod.GET, "/user/*").access(authorize.hasAction(UserActions.GET))
                        .requestMatchers(HttpMethod.PUT, "/user/*").access(authorize.hasAction(UserActions.MODIFY))
                        .requestMatchers(HttpMethod.DELETE, "/user/*").access(authorize.hasAction(UserActions.DELETE))
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .anyRequest().denyAll()
                )
                .build();
    }

}
