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

    /**
     * Checks if given old password of the use is correct
     * and then changes password of the user to given new password
     * @param user the user
     * @param oldPassword the old password
     * @param newPassword the new password
     * @throws IllegalArgumentException if old password is wrong
     */
    void changePassword(User user, String oldPassword, String newPassword) throws IllegalArgumentException;

    /**
     * Changes password of the user to the new password
     * @param user the user
     * @param newPassword the new password
     */
    void changePassword(User user, String newPassword);

    /**
     * Checks if given password is password of the given user
     */
    boolean verifyPassword(User user, String password);

}
