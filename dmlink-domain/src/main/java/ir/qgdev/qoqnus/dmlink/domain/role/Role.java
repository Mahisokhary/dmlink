package ir.qgdev.qoqnus.dmlink.domain.role;

import ir.qgdev.qoqnus.dmlink.domain.action.Action;
import ir.qgdev.qoqnus.dmlink.domain.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Roles of a user determines what the use is permitted to do
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role extends Entity<Role> {
    public Role(
            @NonNull Long id,
            @NonNull String name,
            @NonNull Collection<Role> parents,
            @NonNull Collection<Action> actions
    ) {
        setId(id);
        setName(name);
        setParents(parents);
        setActions(actions);
    }

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
     * Checks if the role has permission to perform given action
     */
    public boolean hasAccess(@NonNull Action action) {
        if (actions.stream().map(Action::getName).anyMatch(action.getName()::equals))
            return true;
        for (Role parent : getParents())
            if (parent.hasAccess(action))
                return true;
        return false;
    }

    public Collection<Action> fetchPermittedActions() {
        return fetchPermittedActionsStream()
                .collect(Collectors.toUnmodifiableSet());
    }

    public Stream<Action> fetchPermittedActionsStream() {
        Stream<Action> parentsActionsStream = getParents().stream()
                .flatMap(Role::fetchPermittedActionsStream);
        Stream<Action> actionsStream = getActions().stream();
        return Stream.concat(parentsActionsStream, actionsStream);
    }

    public Collection<Role> fetchPermittedRoles() {
        return fetchPermittedRolesStream()
                .collect(Collectors.toUnmodifiableSet());
    }

    public Stream<Role> fetchPermittedRolesStream() {
        Stream<Role> parentsRolesStream = getParents().stream()
                .flatMap(Role::fetchPermittedRolesStream);
        Stream<Role> rolesStream = getParents().stream();
        return Stream.concat(parentsRolesStream, rolesStream);
    }

    public void setParents(@NonNull Collection<Role> parents) {
        boolean invalid = parents.stream()
                .flatMap(Role::fetchPermittedRolesStream)
                .map(Role::getId)
                .anyMatch(getId()::equals);
        if (invalid) throw new RoleParentCycleDetectedException();
        this.parents = parents;
    }

}
