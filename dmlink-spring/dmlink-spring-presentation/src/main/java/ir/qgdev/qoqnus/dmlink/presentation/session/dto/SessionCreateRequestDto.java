package ir.qgdev.qoqnus.dmlink.presentation.session.dto;

import lombok.NonNull;

public record SessionCreateRequestDto(
        @NonNull String username,
        @NonNull String password
) {
}
