package ir.qgdev.qoqnus.dmlink.presentation.session.dto;

import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import ir.qgdev.qoqnus.dmlink.domain.session.Session;

public record SessionDto(
        String id,
        String user,
        Role role
) {
    public SessionDto(Session session) {
        this(session.getId().toString(), session.getUser().getId().toString(), session.getRole());
    }
}
