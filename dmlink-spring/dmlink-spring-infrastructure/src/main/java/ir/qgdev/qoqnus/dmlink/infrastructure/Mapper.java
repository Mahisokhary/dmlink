package ir.qgdev.qoqnus.dmlink.infrastructure;

import ir.qgdev.qoqnus.dmlink.domain.Entity;

import java.util.Collection;

/**
 * Map domain entities to jpa entities and vice versa
 */
public interface Mapper<D extends Entity, J> {
    /**
     * Convert given JPA entity to domain entity
     */
    D toDomain(J jpa);

    /**
     * Convert given domain entity to JPA entity
     */
    J toJpa(D domain);

}
