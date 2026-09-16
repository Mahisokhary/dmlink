package ir.qgdev.qoqnus.dmlink.infrastructure.role;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

/**
 * JPA Entity of {@link ir.qgdev.qoqnus.dmlink.domain.role.Role}
 */
@Entity
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleEntity {
    @Column(unique = true, nullable = false, updatable = false)
    @Id
    Long id;

    @Column(unique = true, nullable = false, updatable = false)
    String name;

    @ManyToMany(fetch = FetchType.EAGER)
    Collection<RoleEntity> parents;
}
