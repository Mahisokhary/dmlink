package ir.qgdev.qoqnus.dmlink.infrastructure.config;

import ir.qgdev.qoqnus.dmlink.infrastructure.role.RoleEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConfigEntity {
    /**
     * ID of configuration
     */
    @Id
    private Long id;

    /**
     * Default role of the service
     */
    @ManyToOne
    @JoinColumn(name = "default_role_id")
    private RoleEntity defaultRole;

    /**
     * Default role of the system for anonymous users
     */
    @ManyToOne
    @JoinColumn(name = "anonymous_role_id")
    private RoleEntity anonymousRole;

}
