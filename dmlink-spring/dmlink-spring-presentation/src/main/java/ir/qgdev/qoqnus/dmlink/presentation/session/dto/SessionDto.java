package ir.qgdev.qoqnus.dmlink.presentation.session.dto;

import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import ir.qgdev.qoqnus.dmlink.domain.user.User;

public record SessionDto(
        String id,
        String user,
        String role
) {
    public SessionDto(Session session) {
        this(session.getId().toString(), session.getUser(), session.getRole());
    }

    public SessionDto(String id, User user, Role role) {
        this(id, user.getId().toString(), role == null ? null : role.getId().toString());
    }

}
