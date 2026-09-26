package ir.qgdev.qoqnus.dmlink.presentation.session.dto;

import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import ir.qgdev.qoqnus.dmlink.domain.session.Session;

public record SessionDto(
        Long id,
        Long user,
        Role role
) {
    public SessionDto(Session session) {
        this(session.getId(), session.getUser().getId(), session.getRole());
    }
}
