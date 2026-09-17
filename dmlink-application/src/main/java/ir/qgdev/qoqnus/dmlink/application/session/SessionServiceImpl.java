package ir.qgdev.qoqnus.dmlink.application.session;

import ir.qgdev.qoqnus.dmlink.application.IdGenerator;
import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import ir.qgdev.qoqnus.dmlink.domain.session.SessionRepository;
import ir.qgdev.qoqnus.dmlink.domain.user.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final IdGenerator idGenerator;
    private final SessionRepository repository;

    @Override
    public Session create(User user, Role role) {
        Session session = Session.builder()
                .id(idGenerator.generate())
                .user(user)
                .role(role)
                .build();
        return repository.save(session);
    }
}
