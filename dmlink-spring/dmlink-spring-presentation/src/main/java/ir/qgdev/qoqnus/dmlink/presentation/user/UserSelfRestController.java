package ir.qgdev.qoqnus.dmlink.presentation.user;

import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import ir.qgdev.qoqnus.dmlink.domain.user.UserRepository;
import ir.qgdev.qoqnus.dmlink.presentation.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/@me")
public class UserSelfRestController {
    private final UserRepository userRepository;

    @GetMapping
    public UserDto get(@AuthenticationPrincipal Session session) {
        return new UserDto(session.getUser());
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Session session) {
        userRepository.deleteById(session.getUser().getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
