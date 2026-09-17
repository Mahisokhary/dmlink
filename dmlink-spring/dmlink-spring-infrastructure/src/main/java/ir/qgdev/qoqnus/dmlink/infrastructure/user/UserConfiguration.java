package ir.qgdev.qoqnus.dmlink.infrastructure.user;

import ir.qgdev.qoqnus.dmlink.application.crypto.PasswordHasher;
import ir.qgdev.qoqnus.dmlink.application.user.UserService;
import ir.qgdev.qoqnus.dmlink.application.user.UserServiceImpl;
import ir.qgdev.qoqnus.dmlink.application.IdGenerator;
import ir.qgdev.qoqnus.dmlink.domain.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {
    @Bean
    public UserService userService(IdGenerator idGenerator, UserRepository userRepository, PasswordHasher passwordHasher) {
        return new UserServiceImpl(idGenerator, userRepository, passwordHasher);
    }
}
