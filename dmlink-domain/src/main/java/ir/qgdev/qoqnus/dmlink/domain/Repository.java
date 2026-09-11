package ir.qgdev.qoqnus.dmlink.domain;

import java.util.Collection;
import java.util.Optional;

/**
 * A repository to save and give access to resources
 * @param <T> Type of the entity
 * @param <ID> Type of unique id of the entity
 */
public interface Repository<T, ID> {
    /**
     * Finds entity by its unique ID
     * @param id the unique ID
     * @return Optional that contains the entity, or empty optional if the entity don't exist
     */
    Optional<T> findById(ID id);

    /**
     * Fetches all entities in the repository
     * @return A collection of fetched entities
     */
    Collection<T> findAll();

}
