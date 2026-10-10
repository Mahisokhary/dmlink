package ir.qgdev.qoqnus.dmlink.domain.test.role;

import ir.qgdev.qoqnus.dmlink.domain.action.Action;
import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import ir.qgdev.qoqnus.dmlink.domain.role.RoleParentCycleDetectedException;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Set;

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

    @Test
    public void testFetchPermittedActions() {
        Collection<Action> expectedActions = Set.of(ACTION_NOT_IMPORTANT, ACTION_IMPORTANT, ACTION_VERY_IMPORTANT);
        Collection<Action> fetchedActions = ROLE_ADMIN.fetchPermittedActions();
        assertEquals(expectedActions, fetchedActions);
    }

    @Test
    public void testFetchPermittedRoles() {
        Collection<Role> expectedRoles = Set.of(ROLE_ANONYMOUS, ROLE_USER);
        Collection<Role> fetchedRoles = ROLE_ADMIN.fetchPermittedRoles();
        assertEquals(expectedRoles, fetchedRoles);
    }

    @Test
    public void testParentCycleDetection() {
        Role admin = ROLE_ADMIN.clone();
        Role anonymous = ROLE_ANONYMOUS.clone();
        assertDoesNotThrow(() -> admin.setParents(Set.of(anonymous)));
        assertThrows(RoleParentCycleDetectedException.class, () -> anonymous.setParents(Set.of(ROLE_ADMIN)));
    }

}
