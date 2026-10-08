package ir.qgdev.qoqnus.dmlink.presentation.role;

import ir.qgdev.qoqnus.dmlink.presentation.action.SpringAction;
import lombok.Getter;
import lombok.NonNull;

public enum RoleActions implements SpringAction {
    CREATE("create"),
    MODIFY("modify"),
    DELETE("delete"),
    GET("get"),
    GET_ALL("get.all"),
    GET_BY_NAME("get.name"),
    ;

    private static final String PREFIX = "role.";

    @NonNull
    @Getter
    private final String name;

    RoleActions(@NonNull String name) {
        this.name = PREFIX + name;
    }
}
