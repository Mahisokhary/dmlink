package ir.qgdev.qoqnus.dmlink.presentation.user;

import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import ir.qgdev.qoqnus.dmlink.presentation.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/@me")
public class UserSelfRestController {
    @GetMapping
    public UserDto get(@AuthenticationPrincipal Session session) {
        return new UserDto(session.getUser());
    }

}
