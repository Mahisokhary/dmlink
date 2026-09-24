package ir.qgdev.qoqnus.dmlink.application.user;

import ir.qgdev.qoqnus.dmlink.application.crypto.PasswordHasher;
import ir.qgdev.qoqnus.dmlink.application.IdGenerator;
import ir.qgdev.qoqnus.dmlink.domain.user.User;
import ir.qgdev.qoqnus.dmlink.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final IdGenerator idGenerator;
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    @Override
    public User create(String username, String displayName, String password) {
        User user = User.builder()
                .id(idGenerator.generate())
                .username(username)
                .displayName(displayName)
                .password(passwordHasher.hash(password))
                .role(null)
                .build();
        return userRepository.save(user);
    }

    @Override
    public void changePassword(User user, String oldPassword, String newPassword) {
        if (passwordHasher.verify(user.getPassword(), oldPassword)) {
            changePassword(user, newPassword);
        }
        throw new IllegalArgumentException("password is wrong");
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordHasher.hash(newPassword));
    }

    @Override
    public boolean verifyPassword(User user, String password) {
        return passwordHasher.verify(user.getPassword(), password);
    }
}
