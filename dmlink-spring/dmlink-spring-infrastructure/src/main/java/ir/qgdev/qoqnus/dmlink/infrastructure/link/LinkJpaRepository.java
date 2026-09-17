package ir.qgdev.qoqnus.dmlink.infrastructure.link;

import ir.qgdev.qoqnus.dmlink.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface LinkJpaRepository extends JpaRepository<LinkEntity, Long> {
    Optional<LinkEntity> findByName(String name);

    Collection<LinkEntity> findAllByOwner(User owner);
}
