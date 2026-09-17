package ir.qgdev.qoqnus.dmlink.application;

/**
 * An Id generator
 */
public interface IdGenerator {
    /**
     * Generate a unique id
     * @return A unique id
     */
    long generate();
}
