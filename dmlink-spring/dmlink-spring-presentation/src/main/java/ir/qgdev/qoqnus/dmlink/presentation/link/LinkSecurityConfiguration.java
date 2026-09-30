package ir.qgdev.qoqnus.dmlink.presentation.link;

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
public class LinkSecurityConfiguration {
    @Bean
    @Order(0)
    public SecurityFilterChain linkSecurityFilterChain(HttpSecurity http) {
        return http
                .with(new ApiEndpointSecurityConfigurer())
                .securityMatcher("/api/link/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new QueryParamRequestMatcher(HttpMethod.GET, "/api/link/", "name")).hasAnyRole("LINK_GET_NAME")
                        .requestMatchers(HttpMethod.POST, "/api/link/").hasAnyRole("LINK_CREATE")
                        .requestMatchers(HttpMethod.GET, "/api/link/").hasAnyRole("LINK_GET_ALL")
                        .requestMatchers(HttpMethod.GET, "/api/link/*").hasAnyRole("LINK_GET")
                        .requestMatchers(HttpMethod.PUT, "/api/link/*").hasAnyRole("LINK_MODIFY")
                        .requestMatchers(HttpMethod.DELETE, "/api/link/*").hasAnyRole("LINK_DELETE")
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .anyRequest().denyAll()
                )
                .build();
    }

}
