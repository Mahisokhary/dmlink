package ir.qgdev.qoqnus.dmlink.domain.user;

import ir.qgdev.qoqnus.dmlink.domain.Entity;
import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * A registered user in dmlink
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends Entity {
    /**
     * Unique not-modifiable ID of the user
     */
    @NonNull
    Long id;

    /**
     * Unique username of the user
     * Can only contain: a-z 0-9 and _
     */
    @NonNull
    String username;

    /**
     * Display name of the user
     */
    String displayName;

    /**
     * Hashed password of the user
     */
    @NonNull
    String password;

    /**
     * Role of the user
     */
    Role role;
}
