package ir.qgdev.qoqnus.dmlink.presentation.session.dto;

import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import ir.qgdev.qoqnus.dmlink.presentation.user.dto.UserDto;

public record SessionCreateResponseDto(
        String token,
        SessionDto session,
        UserDto user
) {
    public SessionCreateResponseDto(String token, Session session) {
        this(token, new SessionDto(session), new UserDto(session.getUser()));
    }
}
