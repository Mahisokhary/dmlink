package ir.qgdev.qoqnus.dmlink.presentation.session.dto;

import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import ir.qgdev.qoqnus.dmlink.presentation.user.dto.UserDto;

public record SessionDto(
        Long id,
        UserDto user,
        Role role
) {
    public SessionDto(Session session) {
        this(session.getId(), new UserDto(session.getUser()), session.getRole());
    }
}
