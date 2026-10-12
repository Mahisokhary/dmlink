package ir.qgdev.qoqnus.dmlink.presentation.role.dto;

import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import lombok.NonNull;

import java.util.Collection;

public record RoleDto(
        @NonNull String id,
        @NonNull String name,
        @NonNull Collection<String> parents
) {
    public RoleDto(Role role) {
        this(
                role.getId().toString(),
                role.getName(),
                role.getParents().stream()
                        .map(Role::getId)
                        .map(Object::toString)
                        .toList()
        );
    }

}
