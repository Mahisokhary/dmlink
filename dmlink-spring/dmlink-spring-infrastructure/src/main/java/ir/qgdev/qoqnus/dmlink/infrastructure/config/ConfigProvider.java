package ir.qgdev.qoqnus.dmlink.infrastructure.config;

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

}
