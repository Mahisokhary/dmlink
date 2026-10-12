package ir.qgdev.qoqnus.dmlink.presentation.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.qgdev.qoqnus.dmlink.domain.user.User;

/**
 * DTO for domain entity {@link User}
 */
public record UserDto(
        String id,
        String username,
        @JsonProperty("display_name") String displayName
) {
    public UserDto(User user) {
        this(user.getId().toString(), user.getUsername(), user.getDisplayName());
    }

}
