package ir.qgdev.qoqnus.dmlink.presentation.user;

import ir.qgdev.qoqnus.dmlink.application.user.UserService;
import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import ir.qgdev.qoqnus.dmlink.domain.user.User;
import ir.qgdev.qoqnus.dmlink.domain.user.UserRepository;
import ir.qgdev.qoqnus.dmlink.presentation.user.dto.UserChangePasswordRequestDto;
import ir.qgdev.qoqnus.dmlink.presentation.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/@me")
public class UserSelfRestController {
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping
    public UserDto get(@AuthenticationPrincipal Session session) {
        return new UserDto(session.getUser());
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Session session) {
        userRepository.deleteById(session.getUser().getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> modifyPassword(@AuthenticationPrincipal Session session, @RequestBody UserChangePasswordRequestDto req) {
        User user = session.getUser();
        userService.changePassword(user, req.oldPassword(), req.newPassword());
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
