package ir.qgdev.qoqnus.dmlink.presentation.role.dto;

import lombok.NonNull;

import java.util.Collection;

public record RoleCreateRequestDto(
        @NonNull String name,
        Collection<Long> parents
) {
}
