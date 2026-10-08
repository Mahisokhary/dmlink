package ir.qgdev.qoqnus.dmlink.application.role;

import ir.qgdev.qoqnus.dmlink.application.IdGenerator;
import ir.qgdev.qoqnus.dmlink.domain.action.Action;
import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import ir.qgdev.qoqnus.dmlink.domain.role.RoleRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final IdGenerator idGenerator;
    private final RoleRepository roleRepository;

    @Override
    public Role create(String name, Collection<Role> parents) {
        Role role = Role.builder()
                .id(idGenerator.generate())
                .name(name)
                .parents(parents)
                .actions(List.of())
                .build();
        return roleRepository.save(role);
    }

    @Override
    public boolean hasAccess(@NonNull Action action, Role granted) {
        if (granted == null) {
            Optional<Role> defaultRole = roleRepository.getDefaultRole();
            if (defaultRole.isEmpty()) return false;
            granted = defaultRole.get();
        }
        return granted.hasAccess(action);
    }

}
