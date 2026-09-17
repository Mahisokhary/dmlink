package ir.qgdev.qoqnus.dmlink.infrastructure.link;

import ir.qgdev.qoqnus.dmlink.domain.link.Link;
import ir.qgdev.qoqnus.dmlink.infrastructure.Mapper;
import ir.qgdev.qoqnus.dmlink.infrastructure.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkMapper implements Mapper<Link, LinkEntity> {
    private final UserMapper userMapper;

    @Override
    public Link toDomain(LinkEntity jpa) {
        if (jpa == null) return null;
        return Link.builder()
                .id(jpa.getId())
                .name(jpa.getName())
                .target(jpa.getTarget())
                .owner(userMapper.toDomain(jpa.getOwner()))
                .build();
    }

    @Override
    public LinkEntity toJpa(Link domain) {
        if (domain == null) return null;
        return LinkEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .target(domain.getTarget())
                .owner(userMapper.toJpa(domain.getOwner()))
                .build();
    }
}
