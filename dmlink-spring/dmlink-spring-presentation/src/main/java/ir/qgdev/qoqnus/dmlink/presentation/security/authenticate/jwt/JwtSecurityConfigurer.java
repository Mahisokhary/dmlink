package ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.jwt;

import ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.AbstractAuthenticationHelperSecurityConfigurer;

/**
 * Security Configurer for JWT authentication
 */
public class JwtSecurityConfigurer extends AbstractAuthenticationHelperSecurityConfigurer<JwtSecurityConfigurer> {
    public JwtSecurityConfigurer() {
        super(JwtAuthenticationHelper.class);
    }

}
