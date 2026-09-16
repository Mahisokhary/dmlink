package ir.qgdev.qoqnus.dmlink.infrastructure.config;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Holds service configurations
 */
@Entity
@Table(name = "config")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfigEntity {
    /**
     * ID of configuration
     */
    @Id
    private Long id;

}
