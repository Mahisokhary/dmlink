package ir.qgdev.qoqnus.dmlink.presentation.security;

import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.RoleGrantedAuthority;
import org.jspecify.annotations.NonNull;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class RoleHierarchyImpl implements RoleHierarchy {
    @Override
    public @NonNull Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(@NonNull Collection<? extends GrantedAuthority> authorities) {
        HashSet<GrantedAuthority> reachableAuthorities = new HashSet<>(authorities);
        for (GrantedAuthority authority : authorities) {
            if (authority instanceof RoleGrantedAuthority roleGrantedAuthority) {
                addRecursively(roleGrantedAuthority.role(), reachableAuthorities);
            }
        }
        return Set.copyOf(reachableAuthorities);
    }

    private void addRecursively(Role role, HashSet<GrantedAuthority> authorities) {
        authorities.add(new RoleGrantedAuthority(role));
        for (Role r : role.getParents()) {
            addRecursively(r, authorities);
        }
    }
}
