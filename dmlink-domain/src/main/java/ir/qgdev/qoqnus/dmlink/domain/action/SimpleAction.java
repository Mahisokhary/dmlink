package ir.qgdev.qoqnus.dmlink.domain.action;

import lombok.NonNull;

/**
 * Simple implementation of Action
 */
public record SimpleAction(@NonNull String getName) implements Action {
}
