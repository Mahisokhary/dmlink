package ir.qgdev.qoqnus.dmlink.presentation.security.authenticate;

import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;

public record RoleGrantedAuthority(Role role) implements GrantedAuthority {
    @Override
    public @NonNull String getAuthority() {
        return "ROLE_" + role.getName().toUpperCase();
    }
}
