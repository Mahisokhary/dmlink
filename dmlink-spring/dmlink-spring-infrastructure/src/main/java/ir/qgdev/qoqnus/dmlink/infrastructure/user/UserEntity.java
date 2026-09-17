package ir.qgdev.qoqnus.dmlink.infrastructure.user;

import ir.qgdev.qoqnus.dmlink.infrastructure.role.RoleEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * JPA Entity of {@link ir.qgdev.qoqnus.dmlink.domain.user.User}
 */
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    Long id;

    @Column(unique = true, nullable = false)
    String username;

    String displayName;

    @Column(nullable = false)
    String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    RoleEntity role;
}
