package ir.qgdev.qoqnus.dmlink.domain.role;

import ir.qgdev.qoqnus.dmlink.domain.action.Action;
import ir.qgdev.qoqnus.dmlink.domain.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

/**
 * Roles of a user determines what the use is permitted to do
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role extends Entity {
    /**
     * Unique id of the role
     */
    @NonNull
    Long id;

    /**
     * Unique name of the role
     */
    @NonNull
    String name;

    /**
     * Parents of the role
     * All permission from parents are passed to the child
     */
    @NonNull
    Collection<Role> parents;

    /**
     * Actions that the role is permitted to do
     */
    @NonNull
    Collection<Action> actions;

    /**
     * Check if granted role has access to needed role permissions
     */
    public boolean hasAccess(@NonNull Role granted) {
        if (equals(granted)) return true;
        return granted.getParents().stream().anyMatch(this::hasAccess);
    }
}
