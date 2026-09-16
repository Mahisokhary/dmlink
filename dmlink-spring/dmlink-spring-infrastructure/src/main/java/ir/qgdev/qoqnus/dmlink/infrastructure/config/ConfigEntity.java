package ir.qgdev.qoqnus.dmlink.infrastructure.config;

import ir.qgdev.qoqnus.dmlink.infrastructure.role.RoleEntity;
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

    /**
     * Default role of the service
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_role_id")
    private RoleEntity defaultRole;

}
