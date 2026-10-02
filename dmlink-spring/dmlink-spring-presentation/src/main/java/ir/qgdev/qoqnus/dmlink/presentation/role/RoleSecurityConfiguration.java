package ir.qgdev.qoqnus.dmlink.presentation.role;

import ir.qgdev.qoqnus.dmlink.presentation.security.ApiEndpointSecurityConfigurer;
import ir.qgdev.qoqnus.dmlink.presentation.security.authorize.QueryParamRequestMatcher;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class RoleSecurityConfiguration {
    @Bean
    @Order(0)
    public SecurityFilterChain roleSecurityFilterChain(HttpSecurity http) {
        return http
                .with(new ApiEndpointSecurityConfigurer())
                .securityMatcher("/api/role/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new QueryParamRequestMatcher(HttpMethod.GET, "/api/role/", "name")).hasAnyAuthority("ROLE_ROLE_GET_NAME")
                        .requestMatchers(HttpMethod.POST, "/api/role/").hasAnyAuthority("ROLE_ROLE_CREATE")
                        .requestMatchers(HttpMethod.GET, "/api/role/").hasAnyAuthority("ROLE_ROLE_GET_ALL")
                        .requestMatchers(HttpMethod.GET, "/api/role/*").hasAnyAuthority("ROLE_ROLE_GET")
                        .requestMatchers(HttpMethod.PUT, "/api/role/*").hasAnyAuthority("ROLE_ROLE_MODIFY")
                        .requestMatchers(HttpMethod.DELETE, "/api/role/*").hasAnyAuthority("ROLE_ROLE_DELETE")
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .anyRequest().denyAll()
                )
                .build();
    }
}
