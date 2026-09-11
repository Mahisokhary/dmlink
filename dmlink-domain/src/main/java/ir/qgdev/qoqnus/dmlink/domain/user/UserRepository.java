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

}
