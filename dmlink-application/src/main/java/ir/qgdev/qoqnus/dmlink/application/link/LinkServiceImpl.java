package ir.qgdev.qoqnus.dmlink.application.link;

import ir.qgdev.qoqnus.dmlink.application.IdGenerator;
import ir.qgdev.qoqnus.dmlink.domain.link.Link;
import ir.qgdev.qoqnus.dmlink.domain.link.LinkRepository;
import ir.qgdev.qoqnus.dmlink.domain.url.HttpUrl;
import ir.qgdev.qoqnus.dmlink.domain.user.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {
    private final IdGenerator idGenerator;
    private final LinkRepository linkRepository;

    @Override
    public Link create(String name, HttpUrl target, User owner) {
        Link link = Link.builder()
                .id(idGenerator.generate())
                .name(name)
                .target(target)
                .owner(owner)
                .build();
        return linkRepository.save(link);
    }
}
