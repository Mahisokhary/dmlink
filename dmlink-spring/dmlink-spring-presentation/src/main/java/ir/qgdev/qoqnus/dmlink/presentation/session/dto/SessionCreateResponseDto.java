package ir.qgdev.qoqnus.dmlink.presentation.session.dto;

import ir.qgdev.qoqnus.dmlink.domain.session.Session;

public record SessionCreateResponseDto(
        String token,
        SessionDto session
) {
    public SessionCreateResponseDto(String token, Session session) {
        this(token, new SessionDto(session));
    }
}
