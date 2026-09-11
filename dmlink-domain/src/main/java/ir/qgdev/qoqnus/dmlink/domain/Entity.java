package ir.qgdev.qoqnus.dmlink.domain;

import java.util.Objects;

/**
 * A domain entity
 */
public abstract class Entity {
    /**
     * Unique id of the entity
     */
    public abstract Long getId();

    /**
     * Checks if object has the same exact type and has same exact ID
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Entity e && o.getClass() == getClass()) {
            return Objects.equals(getId(), e.getId());
        }
        return false;
    }

    /**
     * Use unique ID of entity to create a hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
