package ir.qgdev.qoqnus.dmlink.infrastructure.user;

import ir.qgdev.qoqnus.dmlink.domain.user.User;
import ir.qgdev.qoqnus.dmlink.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository repo;
    private final UserMapper mapper;

    @Override
    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username).map(mapper::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repo.existsByUsername(username);
    }

    @Override
    public void deleteByUsername(String username) {
        repo.deleteByUsername(username);
    }

    @Override
    public Optional<User> findById(Long id) {
        return repo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Collection<User> findAll() {
        return mapper.toDomain(repo.findAll());
    }

    @Override
    public User save(User entity) {
        return mapper.toDomain(repo.save(mapper.toJpa(entity)));
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
