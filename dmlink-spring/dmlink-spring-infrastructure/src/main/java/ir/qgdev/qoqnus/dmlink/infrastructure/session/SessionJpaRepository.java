package ir.qgdev.qoqnus.dmlink.infrastructure.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionJpaRepository extends JpaRepository<SessionEntity, Long> {
}
