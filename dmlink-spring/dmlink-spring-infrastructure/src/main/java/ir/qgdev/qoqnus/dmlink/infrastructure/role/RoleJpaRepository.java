package ir.qgdev.qoqnus.dmlink.infrastructure.role;

import ir.qgdev.qoqnus.dmlink.domain.link.Link;
import ir.qgdev.qoqnus.dmlink.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}
