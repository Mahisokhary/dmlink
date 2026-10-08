package ir.qgdev.qoqnus.dmlink.presentation.security.authorize;

import ir.qgdev.qoqnus.dmlink.application.role.RoleService;
import ir.qgdev.qoqnus.dmlink.domain.action.Action;
import ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.RoleGrantedAuthority;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

@RequiredArgsConstructor
class ActionAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    private final RoleService roleService;
    private final Action action;

    @Override
    public @Nullable AuthorizationResult authorize(@NonNull Supplier<? extends @Nullable Authentication> authenticationSupplier, RequestAuthorizationContext ctx) {
        Authentication authentication = authenticationSupplier.get();
        return new AuthorizationDecision(hasAccess(authentication));
    }

    private boolean hasAccess(Authentication authentication) {
        if (action instanceof GrantedAuthority grantedAuthority) {
            String authority = grantedAuthority.getAuthority();
            if (authority != null) {
                boolean shallowResult = authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .anyMatch(authority::equals);
                if (shallowResult) return true;
            }
        }

        return authentication.getAuthorities().stream()
                .filter(RoleGrantedAuthority.class::isInstance)
                .map(RoleGrantedAuthority.class::cast)
                .map(RoleGrantedAuthority::role)
                .anyMatch(r -> roleService.hasAccess(action, r));
    }

}
