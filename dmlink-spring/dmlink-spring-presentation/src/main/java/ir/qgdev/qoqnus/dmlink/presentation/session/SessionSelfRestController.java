package ir.qgdev.qoqnus.dmlink.presentation.session;

import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import ir.qgdev.qoqnus.dmlink.presentation.session.dto.SessionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Let sessions access themselves
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/session/@me")
public class SessionSelfRestController {
    @GetMapping
    public SessionDto get(@AuthenticationPrincipal Session session) {
        return new SessionDto(session);
    }

}
