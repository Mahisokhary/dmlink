package ir.qgdev.qoqnus.dmlink.presentation.role.dto;

import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import lombok.NonNull;

import java.util.Collection;

public record RoleDto(
        @NonNull Long id,
        @NonNull String name,
        @NonNull Collection<Long> parents
) {
    public RoleDto(Role role) {
        this(
                role.getId(),
                role.getName(),
                role.getParents().stream()
                        .map(Role::getId)
                        .toList()
        );
    }

}
