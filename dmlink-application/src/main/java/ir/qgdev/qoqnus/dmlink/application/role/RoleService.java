package ir.qgdev.qoqnus.dmlink.application.role;

import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import lombok.NonNull;

import java.util.Collection;
import java.util.Collections;

/**
 * Role Service
 */
public interface RoleService {
    /**
     * Creates a role with given name
     *
     * @param name name of the role
     * @return Created role
     */
    default Role create(String name) {
        return create(name, Collections.emptySet());
    }

    /**
     * Creates a role with given name and parents
     * @param name name of the role
     * @param parents parents of the role
     * @return Created role
     */
    Role create(String name, Collection<Role> parents);

    /**
     * Check if granted role has access to needed role permissions
     * If granted is null default role is used instead
     */
    boolean hasAccess(@NonNull Role needed, Role granted);

}
