package ir.qgdev.qoqnus.dmlink.infrastructure.role;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RoleEntity {
    @Column(unique = true, nullable = false, updatable = false)
    @Id
    Long id;

    @Column(unique = true, nullable = false, updatable = false)
    String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    Collection<RoleEntity> parents;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "role_action")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    Collection<String> actions;

}
