package ir.qgdev.qoqnus.dmlink.domain.link;

import ir.qgdev.qoqnus.dmlink.domain.Repository;
import ir.qgdev.qoqnus.dmlink.domain.user.User;

import java.util.Collection;
import java.util.Optional;

/**
 * A repository to save and give access to links
 */
public interface LinkRepository extends Repository<Link, Long> {
}
