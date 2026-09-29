package ir.qgdev.qoqnus.dmlink.domain.session;

import ir.qgdev.qoqnus.dmlink.domain.Entity;
import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import ir.qgdev.qoqnus.dmlink.domain.user.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * A session for a user
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Session extends Entity {
    /**
     * Unique not-modifiable ID of the session
     */
    @NonNull
    Long id;

    /**
     * Session owner
     */
    @NonNull
    User user;

    /**
     * Role of the session
     */
    Role role;
}
