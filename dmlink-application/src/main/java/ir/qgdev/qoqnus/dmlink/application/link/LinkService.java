package ir.qgdev.qoqnus.dmlink.application.link;

import ir.qgdev.qoqnus.dmlink.domain.link.Link;
import ir.qgdev.qoqnus.dmlink.domain.url.HttpUrl;
import ir.qgdev.qoqnus.dmlink.domain.user.User;

/**
 * Link Service
 */
public interface LinkService {
    /**
     * Creates a link with given info
     * @param name Name of the link
     * @param target Target url of the link
     * @param owner Owner of the link
     * @return The created link
     */
    Link create(String name, HttpUrl target, User owner);

}
