package ir.qgdev.qoqnus.dmlink.domain.link;

import ir.qgdev.qoqnus.dmlink.domain.Entity;
import ir.qgdev.qoqnus.dmlink.domain.url.HttpUrl;
import ir.qgdev.qoqnus.dmlink.domain.user.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * A link shorten by the service
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor()
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Link extends Entity<Link> {
    /**
     * Unique not-modifiable ID of the user
     */
    @NonNull
    Long id;

    /**
     * Unique name of the link
     */
    @NonNull
    String name;

    /**
     * Target url of the link
     */
    @NonNull
    HttpUrl target;

    /**
     * Owner of the link
     */
    User owner;
}
