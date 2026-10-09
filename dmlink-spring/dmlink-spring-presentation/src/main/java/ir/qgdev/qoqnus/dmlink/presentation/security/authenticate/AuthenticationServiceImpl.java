package ir.qgdev.qoqnus.dmlink.presentation.security.authenticate;

import ir.qgdev.qoqnus.dmlink.domain.action.Action;
import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public Collection<Action> fetchPermittedActions(Authentication authentication) {
        if (authentication == null) return Set.of();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        Stream<Action> fetchedActions = authorities.stream()
                .filter(RoleGrantedAuthority.class::isInstance)
                .map(RoleGrantedAuthority.class::cast)
                .map(RoleGrantedAuthority::role)
                .flatMap(Role::fetchPermittedActionsStream);
        Stream<Action> grantedActions = authorities.stream()
                .filter(Action.class::isInstance)
                .map(Action.class::cast);

        return Stream.concat(fetchedActions, grantedActions)
                .collect(Collectors.toUnmodifiableSet());
    }

}
