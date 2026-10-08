package ir.qgdev.qoqnus.dmlink.domain.test.role;

import ir.qgdev.qoqnus.dmlink.domain.role.Role;

import java.util.Set;

import static ir.qgdev.qoqnus.dmlink.domain.test.action.ActionConstants.*;

public final class RoleConstants {
    public static final Role ROLE_ANONYMOUS = Role.builder()
            .id(0L)
            .name("anonymous")
            .actions(Set.of(ACTION_NOT_IMPORTANT))
            .parents(Set.of())
            .build();
    public static final Role ROLE_USER = Role.builder()
            .id(1L)
            .name("user")
            .actions(Set.of(ACTION_IMPORTANT))
            .parents(Set.of(ROLE_ANONYMOUS))
            .build();
    public static final Role ROLE_ADMIN = Role.builder()
            .id(2L)
            .name("admin")
            .actions(Set.of(ACTION_VERY_IMPORTANT))
            .parents(Set.of(ROLE_USER))
            .build();

}
