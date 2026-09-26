package ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.jwt;

/**
 * Provides methods to create and verify JSON Web Tokens
 */
public interface JwtService {
    /**
     * Create a JWT for given ID
     * @param id id
     * @return generated JWT
     */
    String create(String id);

    /**
     * Verifies the JWT and extract ID of it
     * @param jwt the JWT
     * @return extracted ID
     * @throws IllegalArgumentException If jwt is invalid
     */
    String verifyAndFetchId(String jwt) throws IllegalArgumentException;
}
