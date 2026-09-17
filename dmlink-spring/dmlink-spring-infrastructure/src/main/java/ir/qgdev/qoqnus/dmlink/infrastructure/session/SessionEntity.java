package ir.qgdev.qoqnus.dmlink.infrastructure.session;

import ir.qgdev.qoqnus.dmlink.infrastructure.role.RoleEntity;
import ir.qgdev.qoqnus.dmlink.infrastructure.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * JPA Entity of {@link ir.qgdev.qoqnus.dmlink.domain.session.Session}
 */
@Entity
@Table(name = "session")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SessionEntity {
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    RoleEntity role;
}
