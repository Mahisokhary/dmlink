package ir.qgdev.qoqnus.dmlink.presentation.session;

import ir.qgdev.qoqnus.dmlink.presentation.action.SpringAction;
import lombok.Getter;
import lombok.NonNull;

public enum SessionActions implements SpringAction {
    CREATE("create"),
    DELETE("delete"),
    GET("get"),
    GET_ALL("get.all"),

    GET_SELF("get.self"),
    DELETE_SELF("get.self"),
    ;

    private static final String PREFIX = "session.";

    @NonNull
    @Getter
    private final String name;

    SessionActions(@NonNull String name) {
        this.name = PREFIX + name;
    }

}
