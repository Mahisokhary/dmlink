package ir.qgdev.qoqnus.dmlink.infrastructure.session;

import ir.qgdev.qoqnus.dmlink.application.IdGenerator;
import ir.qgdev.qoqnus.dmlink.application.session.SessionService;
import ir.qgdev.qoqnus.dmlink.application.session.SessionServiceImpl;
import ir.qgdev.qoqnus.dmlink.application.user.UserService;
import ir.qgdev.qoqnus.dmlink.domain.session.SessionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionConfiguration {
    @Bean
    public SessionService sessionService(IdGenerator idGenerator, SessionRepository sessionRepository, UserService userService) {
        return new SessionServiceImpl(idGenerator, sessionRepository, userService);
    }
}
