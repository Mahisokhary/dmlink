package ir.qgdev.qoqnus.dmlink.infrastructure.role;

import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {
    @QueryHints({
            @QueryHint(name = "org.hibernate.cacheable", value = "true")
    })
    Optional<RoleEntity> findByName(String name);
}
