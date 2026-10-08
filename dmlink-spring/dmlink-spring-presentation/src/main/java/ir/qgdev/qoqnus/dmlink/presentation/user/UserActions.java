package ir.qgdev.qoqnus.dmlink.presentation.user;

import ir.qgdev.qoqnus.dmlink.presentation.action.SpringAction;
import lombok.Getter;
import lombok.NonNull;

public enum UserActions implements SpringAction {
    CREATE("create"),
    MODIFY("modify"),
    DELETE("delete"),
    GET("get"),
    GET_ALL("get.all"),
    GET_BY_USERNAME("get.username"),

    GET_SELF("get.self"),
    DELETE_SELF("get.self"),
    MODIFY_SELF_PASSWORD("modify.self.password"),
    ;

    private static final String PREFIX = "user.";

    @NonNull
    @Getter
    private final String name;

    UserActions(@NonNull String name) {
        this.name = PREFIX + name;
    }

}
