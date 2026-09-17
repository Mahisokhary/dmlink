package ir.qgdev.qoqnus.dmlink.infrastructure.link;

import ir.qgdev.qoqnus.dmlink.application.link.LinkService;
import ir.qgdev.qoqnus.dmlink.application.link.LinkServiceImpl;
import ir.qgdev.qoqnus.dmlink.domain.IdGenerator;
import ir.qgdev.qoqnus.dmlink.domain.link.LinkRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkConfiguration {
    @Bean
    public LinkService linkService(IdGenerator idGenerator, LinkRepository linkRepository) {
        return new LinkServiceImpl(idGenerator, linkRepository);
    }
}
