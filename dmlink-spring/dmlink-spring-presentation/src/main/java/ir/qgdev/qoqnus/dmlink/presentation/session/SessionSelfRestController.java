package ir.qgdev.qoqnus.dmlink.presentation.session;

import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import ir.qgdev.qoqnus.dmlink.domain.session.SessionRepository;
import ir.qgdev.qoqnus.dmlink.presentation.session.dto.SessionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private final SessionRepository sessionRepository;

    @GetMapping
    public SessionDto get(@AuthenticationPrincipal Session session) {
        return new SessionDto(session);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Session session) {
        sessionRepository.deleteById(session.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
