package ir.qgdev.qoqnus.dmlink.infrastructure.crypto;

import ir.qgdev.qoqnus.dmlink.application.crypto.PasswordHasher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Implementation of application {@link PasswordHasher} backed by spring security crypto {@link PasswordEncoder}
 */
@Component
@RequiredArgsConstructor
public class PasswordHasherImpl implements PasswordHasher {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String hash(String string) {
        return passwordEncoder.encode(string);
    }

    @Override
    public boolean verify(String hashedPassword, String rawPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
