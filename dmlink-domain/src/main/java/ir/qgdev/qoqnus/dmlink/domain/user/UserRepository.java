package ir.qgdev.qoqnus.dmlink.domain.user;

import ir.qgdev.qoqnus.dmlink.domain.Repository;

import java.util.Optional;

/**
 * A repository to save and give access to users
 */
public interface UserRepository extends Repository<User, Long> {
    /**
     * Finds user by its unique username
     * @param username the unique username
     * @return Optional that contains the user, or empty optional if the user don't exist
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a user with given username exists
     * @param username the username
     * @return whether the user exists or not
     */
    boolean existsByUsername(String username);

    /**
     * Deletes user with given username
     * @param username the username
     */
    void deleteByUsername(String username);

}
