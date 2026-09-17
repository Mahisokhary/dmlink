package ir.qgdev.qoqnus.dmlink.infrastructure.user;

import ir.qgdev.qoqnus.dmlink.domain.user.User;
import ir.qgdev.qoqnus.dmlink.infrastructure.Mapper;
import ir.qgdev.qoqnus.dmlink.infrastructure.role.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<User, UserEntity> {
    private final RoleMapper roleMapper;

    @Override
    public User toDomain(UserEntity jpa) {
        if (jpa == null) return null;
        return User.builder()
                .id(jpa.getId())
                .username(jpa.getUsername())
                .displayName(jpa.getDisplayName())
                .password(jpa.getPassword())
                .role(roleMapper.toDomain(jpa.getRole()))
                .build();
    }

    @Override
    public UserEntity toJpa(User domain) {
        if (domain == null) return null;
        return UserEntity.builder()
                .id(domain.getId())
                .username(domain.getUsername())
                .displayName(domain.getDisplayName())
                .password(domain.getPassword())
                .role(roleMapper.toJpa(domain.getRole()))
                .build();
    }
}
