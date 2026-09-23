package ir.qgdev.qoqnus.dmlink.presentation.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

/**
 * Create request DTO for domain entity {@link ir.qgdev.qoqnus.dmlink.domain.user.User}
 */
public record UserCreateRequestDto(
        @NonNull String username,
        @JsonProperty("display_name") String displayName,
        @NonNull String password
) {
}
