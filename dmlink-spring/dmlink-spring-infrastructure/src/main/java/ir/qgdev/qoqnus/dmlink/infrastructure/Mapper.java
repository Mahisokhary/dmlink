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

    /**
     * Convert given collection of JPA entity to a collection of domain entity
     */
    default Collection<D> toDomain(Collection<? extends J> jpa) {
        return jpa.stream()
                .map(this::toDomain)
                .toList();
    }

    /**
     * Convert given collection of domain entity to a collection of JPA entity
     */
    default Collection<J> toJpa(Collection<? extends D> domain) {
        return domain.stream()
                .map(this::toJpa)
                .toList();
    }

}
