package ir.qgdev.qoqnus.dmlink.presentation.security.authenticate;

import jakarta.servlet.Filter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Create an authentication filter for given authentication helper and add it at specified position
 */
@RequiredArgsConstructor
public abstract class AbstractAuthenticationHelperSecurityConfigurer<C extends AbstractAuthenticationHelperSecurityConfigurer<C>> extends AbstractHttpConfigurer<C, HttpSecurity> {
    /**
     * Relative position of filter to relative filter
     */
    public enum RelativePosition {
        /**
         * Filter should be added before relative filter
         */
        BEFORE,
        /**
         * Filter should be added after relative filter
         */
        AFTER
    }

    @NonNull private final Class<? extends AuthenticationHelper> authenticationHelperClass;
    @NonNull private final Class<? extends Filter> relativeFilter;
    @NonNull private final RelativePosition relativePositionToRelativeFilter;

    public AbstractAuthenticationHelperSecurityConfigurer(Class<? extends AuthenticationHelper> authenticationHelperClass) {
        this(authenticationHelperClass, UsernamePasswordAuthenticationFilter.class, RelativePosition.BEFORE);
    }

    @Override
    public void configure(HttpSecurity http) {
        ApplicationContext context = http.getSharedObject(ApplicationContext.class);
        AuthenticationManager authenticationManager = context.getBean(AuthenticationManager.class);
        AuthenticationHelper helper = context.getBean(authenticationHelperClass);
        AuthenticationFilter filter = new AuthenticationFilter(authenticationManager, helper);
        filter.setSuccessHandler(helper);
        filter.setFailureHandler(helper);
        switch (relativePositionToRelativeFilter) {
            case BEFORE -> http.addFilterBefore(filter, relativeFilter);
            case AFTER -> http.addFilterAfter(filter, relativeFilter);
        }
    }
}
