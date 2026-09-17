package ir.qgdev.qoqnus.dmlink.infrastructure.session;

import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import ir.qgdev.qoqnus.dmlink.infrastructure.Mapper;
import ir.qgdev.qoqnus.dmlink.infrastructure.role.RoleMapper;
import ir.qgdev.qoqnus.dmlink.infrastructure.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionMapper implements Mapper<Session, SessionEntity> {
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Override
    public Session toDomain(SessionEntity jpa) {
        return Session.builder()
                .id(jpa.getId())
                .user(userMapper.toDomain(jpa.getUser()))
                .role(roleMapper.toDomain(jpa.getRole()))
                .build();
    }

    @Override
    public SessionEntity toJpa(Session domain) {
        return SessionEntity.builder()
                .id(domain.getId())
                .user(userMapper.toJpa(domain.getUser()))
                .role(roleMapper.toJpa(domain.getRole()))
                .build();
    }
}
