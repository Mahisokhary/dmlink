package ir.qgdev.qoqnus.dmlink.application.user;

import ir.qgdev.qoqnus.dmlink.domain.user.User;

/**
 * User Service
 */
public interface UserService {
    /**
     * Create a user
     * @param username Unique username of the user
     * @param displayName Display name of the user
     * @param password Password of the user
     * @return The created user
     */
    User create(String username, String displayName, String password);

}
