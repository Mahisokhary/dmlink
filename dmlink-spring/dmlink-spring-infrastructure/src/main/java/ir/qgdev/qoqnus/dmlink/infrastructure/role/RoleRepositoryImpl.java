package ir.qgdev.qoqnus.dmlink.infrastructure.role;

import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import ir.qgdev.qoqnus.dmlink.domain.role.RoleRepository;
import ir.qgdev.qoqnus.dmlink.infrastructure.config.ConfigProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    private final RoleJpaRepository repo;
    private final RoleMapper mapper;
    private final ConfigProvider config;

    @Override
    public Optional<Role> findByName(String name) {
        return repo.findByName(name).map(mapper::toDomain);
    }

    @Override
    public Optional<Role> getDefaultRole() {
        return Optional.ofNullable(mapper.toDomain(config.getDefaultRole()));
    }

    @Override
    public Optional<Role> findById(Long id) {
        return repo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Collection<Role> findAll() {
        return mapper.toDomain(repo.findAll());
    }

    @Override
    public Role save(Role entity) {
        return mapper.toDomain(repo.save(mapper.toJpa(entity)));
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
