package ir.qgdev.qoqnus.dmlink.domain.test.role;

import org.junit.jupiter.api.Test;

import static ir.qgdev.qoqnus.dmlink.domain.test.action.ActionConstants.*;
import static ir.qgdev.qoqnus.dmlink.domain.test.role.RoleConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {
    @Test
    public void testHasAccess() {
        assertTrue(ROLE_ANONYMOUS.hasAccess(ACTION_NOT_IMPORTANT), "Anonymous role don't have access to non important action");
        assertFalse(ROLE_ANONYMOUS.hasAccess(ACTION_VERY_IMPORTANT), "Anonymous role have access to very important action");

        assertTrue(ROLE_USER.hasAccess(ACTION_NOT_IMPORTANT), "User role don't have access to non important action");
        assertTrue(ROLE_USER.hasAccess(ACTION_IMPORTANT), "User role don't have access to important action");
        assertFalse(ROLE_USER.hasAccess(ACTION_VERY_IMPORTANT), "User role have access to very important action");

        assertTrue(ROLE_ADMIN.hasAccess(ACTION_NOT_IMPORTANT), "User role don't have access to non important action");
        assertTrue(ROLE_ADMIN.hasAccess(ACTION_IMPORTANT), "User role don't have access to important action");
        assertTrue(ROLE_ADMIN.hasAccess(ACTION_VERY_IMPORTANT), "User role don't have access to very important action");
    }

}
