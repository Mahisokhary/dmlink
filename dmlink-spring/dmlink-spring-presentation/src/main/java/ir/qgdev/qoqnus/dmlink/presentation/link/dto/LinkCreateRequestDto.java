package ir.qgdev.qoqnus.dmlink.presentation.link.dto;

import ir.qgdev.qoqnus.dmlink.domain.url.HttpUrl;
import lombok.NonNull;

public record LinkCreateRequestDto(
        @NonNull String name,
        @NonNull HttpUrl target
) {
}
