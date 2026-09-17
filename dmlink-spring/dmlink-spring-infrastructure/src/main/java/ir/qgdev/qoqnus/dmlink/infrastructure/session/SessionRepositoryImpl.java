package ir.qgdev.qoqnus.dmlink.infrastructure.session;

import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import ir.qgdev.qoqnus.dmlink.domain.session.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SessionRepositoryImpl implements SessionRepository {
    private final SessionMapper mapper;
    private final SessionJpaRepository repo;

    @Override
    public Optional<Session> findById(Long id) {
        return repo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Collection<Session> findAll() {
        return mapper.toDomain(repo.findAll());
    }

    @Override
    public Session save(Session entity) {
        return mapper.toDomain(repo.save(mapper.toJpa(entity)));
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
