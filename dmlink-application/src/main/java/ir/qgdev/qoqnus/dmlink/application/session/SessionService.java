package ir.qgdev.qoqnus.dmlink.application.session;

import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import ir.qgdev.qoqnus.dmlink.domain.user.User;

import java.util.Optional;

/**
 * Session Service
 */
public interface SessionService {
    /**
     * Creates new session for given user and with the user role
     * @param user the user
     * @return created session
     */
    default Session create(User user) {
        return create(user, user.getRole());
    }

    /**
     * Creates new session for given user and with the given user
     * @param user the user
     * @param role the role
     * @return create session
     */
    Session create(User user, Role role);

    /**
     * Validates username and password and create a new session
     * @param username Username of the user the session should be created for
     * @param password Password of the user the session should be created for
     * @return Optional containing created session (if username password is correct)
     */
    Optional<Session> login(String username, String password);

}
