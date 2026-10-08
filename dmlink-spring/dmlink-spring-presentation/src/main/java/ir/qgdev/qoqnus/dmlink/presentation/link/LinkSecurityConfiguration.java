package ir.qgdev.qoqnus.dmlink.presentation.link;

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
public class LinkSecurityConfiguration {
    private final AuthorizationManagerFactory authorize;

    @Bean
    @Order(0)
    public SecurityFilterChain linkSecurityFilterChain(HttpSecurity http) {
        return http
                .with(new ApiEndpointSecurityConfigurer())
                .securityMatcher("/api/link/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new QueryParamRequestMatcher(HttpMethod.GET, "/api/link/", "name")).access(authorize.hasAction(LinkActions.GET_BY_NAME))
                        .requestMatchers(HttpMethod.POST, "/api/link/").access(authorize.hasAction(LinkActions.CREATE))
                        .requestMatchers(HttpMethod.GET, "/api/link/").access(authorize.hasAction(LinkActions.GET_ALL))
                        .requestMatchers(HttpMethod.GET, "/api/link/*").access(authorize.hasAction(LinkActions.GET))
                        .requestMatchers(HttpMethod.PUT, "/api/link/*").access(authorize.hasAction(LinkActions.MODIFY))
                        .requestMatchers(HttpMethod.DELETE, "/api/link/*").access(authorize.hasAction(LinkActions.DELETE))
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .anyRequest().denyAll()
                )
                .build();
    }

}
