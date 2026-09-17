package ir.qgdev.qoqnus.dmlink.infrastructure.link;

import ir.qgdev.qoqnus.dmlink.domain.link.Link;
import ir.qgdev.qoqnus.dmlink.domain.link.LinkRepository;
import ir.qgdev.qoqnus.dmlink.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LinkRepositoryImpl implements LinkRepository {
    private final LinkJpaRepository repo;
    private final LinkMapper mapper;

    @Override
    public Optional<Link> findByName(String name) {
        return repo.findByName(name).map(mapper::toDomain);
    }

    @Override
    public Collection<Link> findAllByOwner(User owner) {
        return mapper.toDomain(repo.findAllByOwner(owner));
    }

    @Override
    public Optional<Link> findById(Long id) {
        return repo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Collection<Link> findAll() {
        return mapper.toDomain(repo.findAll());
    }

    @Override
    public Link save(Link entity) {
        return mapper.toDomain(repo.save(mapper.toJpa(entity)));
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
