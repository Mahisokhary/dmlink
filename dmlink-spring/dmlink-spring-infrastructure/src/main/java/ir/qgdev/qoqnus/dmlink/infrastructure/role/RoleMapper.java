package ir.qgdev.qoqnus.dmlink.infrastructure.role;

import ir.qgdev.qoqnus.dmlink.domain.action.Action;
import ir.qgdev.qoqnus.dmlink.domain.action.SimpleAction;
import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import ir.qgdev.qoqnus.dmlink.infrastructure.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleMapper implements Mapper<Role, RoleEntity> {
    @Override
    public Role toDomain(RoleEntity jpa) {
        if (jpa == null) return null;
        return Role.builder()
                .id(jpa.getId())
                .name(jpa.getName())
                .parents(jpa.getParents().stream().map(this::toDomain).toList())
                .actions(jpa.getActions().stream().<Action>map(SimpleAction::new).toList())
                .build();
    }

    @Override
    public RoleEntity toJpa(Role domain) {
        if (domain == null) return null;
        return RoleEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .parents(domain.getParents().stream().map(this::toJpa).toList())
                .actions(domain.getActions().stream().map(Action::getName).toList())
                .build();
    }
}
