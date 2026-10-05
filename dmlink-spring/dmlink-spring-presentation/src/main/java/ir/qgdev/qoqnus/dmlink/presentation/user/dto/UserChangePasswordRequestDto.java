package ir.qgdev.qoqnus.dmlink.presentation.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

public record UserChangePasswordRequestDto(
        @JsonProperty("old_password") @NonNull String oldPassword,
        @JsonProperty("new_password") @NonNull String newPassword
) {
}
