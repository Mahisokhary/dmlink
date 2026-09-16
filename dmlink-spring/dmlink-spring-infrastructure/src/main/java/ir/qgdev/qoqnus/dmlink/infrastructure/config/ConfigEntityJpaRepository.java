package ir.qgdev.qoqnus.dmlink.infrastructure.config;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigEntityJpaRepository extends JpaRepository<ConfigEntity, Long> {
}
