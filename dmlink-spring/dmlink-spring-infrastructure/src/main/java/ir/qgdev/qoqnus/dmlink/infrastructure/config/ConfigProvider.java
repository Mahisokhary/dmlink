package ir.qgdev.qoqnus.dmlink.infrastructure.config;

import ir.qgdev.qoqnus.dmlink.infrastructure.role.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Provides service default configurations
 */
@Component
@RequiredArgsConstructor
public class ConfigProvider {
    private final ConfigEntityJpaRepository repo;

    public ConfigEntity get() {
        return repo.findById(0L).orElseGet(() -> {
            ConfigEntity entity = ConfigEntity.builder()
                    .id(0L)
                    .build();
            return repo.save(entity);
        });
    }

    /**
     * Returns default role of the service
     */
    public RoleEntity getDefaultRole() {
        return get().getDefaultRole();
    }

    /**
     * Returns default role of the system for anonymous users
     */
    public RoleEntity getAnonymousRole() {
        return get().getAnonymousRole();
    }

}
