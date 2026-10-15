package ir.qgdev.qoqnus.dmlink.presentation.link.dto;

import ir.qgdev.qoqnus.dmlink.domain.link.Link;
import ir.qgdev.qoqnus.dmlink.domain.user.User;
import lombok.NonNull;

public record LinkDto(
        @NonNull String id,
        @NonNull String name,
        @NonNull String target,
        String owner
) {
    public LinkDto(Link link) {
        this(link.getId().toString(), link.getName(), link.getTarget().toString(), link.getOwner());
    }

    public LinkDto(String id, String name, String target, User owner) {
        this(id, name, target, owner == null ? null : owner.getId().toString());
    }
}
