package ir.qgdev.qoqnus.dmlink.application.crypto;

/**
 * Password Hasher
 */
public interface PasswordHasher extends Hasher {
    /**
     * Checks if the given raw password is same as the hashed password
     * @param hashedPassword the hashed password
     * @param rawPassword the raw password
     * @return Whether raw password is same as hashed password
     */
    default boolean verify(String hashedPassword, String rawPassword) {
        return hash(rawPassword).equals(rawPassword);
    }
}
