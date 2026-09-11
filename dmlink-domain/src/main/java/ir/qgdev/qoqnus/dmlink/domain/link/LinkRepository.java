package ir.qgdev.qoqnus.dmlink.domain.link;

import ir.qgdev.qoqnus.dmlink.domain.Repository;
import ir.qgdev.qoqnus.dmlink.domain.user.User;

import java.util.Collection;
import java.util.Optional;

/**
 * A repository to save and give access to links
 */
public interface LinkRepository extends Repository<Link, Long> {
    /**
     * Finds link by its unique name
     * @param name the unique name of the link
     * @return Optional that contains the link, or empty optional if the link don't exist
     */
    Optional<Link> findByName(String name);

    /**
     * Fetches a collection of all links owned by the given user
     * @param owner the user
     * @return Collection of fetched links
     */
    Collection<Link> findAllByOwner(User owner);

}
