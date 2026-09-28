package ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.anonymous;

import ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.AbstractAuthenticationHelperSecurityConfigurer;
import org.springframework.security.web.context.SecurityContextHolderFilter;

public class AnonymousConfigurer extends AbstractAuthenticationHelperSecurityConfigurer<AnonymousConfigurer> {
    public AnonymousConfigurer() {
        super(AnonymousAuthenticationHelper.class, SecurityContextHolderFilter.class, RelativePosition.BEFORE);
    }

}
