package ir.qgdev.qoqnus.dmlink.domain.role;

import ir.qgdev.qoqnus.dmlink.domain.Repository;

import java.util.Optional;

/**
 * A repository to save and give access to links
 */
public interface RoleRepository extends Repository<Role, Long> {
    /**
     * Finds role by its unique name
     * @param name the unique name of the role
     * @return Optional that contains the role, or empty optional if the role don't exist
     */
    Optional<Role> findByName(String name);

    /**
     * Returns default role that should be assigned to users
     */
    Optional<Role> getDefaultRole();

}
