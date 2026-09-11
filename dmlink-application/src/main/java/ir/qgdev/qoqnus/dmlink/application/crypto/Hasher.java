package ir.qgdev.qoqnus.dmlink.application.crypto;

/**
 * A Hasher
 */
public interface Hasher {
    /**
     * Hashes given string
     * @param string string to be hashed
     * @return Hashed string
     */
    String hash(String string);
}
