package ir.qgdev.qoqnus.dmlink.domain.action;

import lombok.NonNull;

/**
 * An action to an entity
 */
public interface Action {
    /**
     * Name of the action
     */
    @NonNull String getName();

}
