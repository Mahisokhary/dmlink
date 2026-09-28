package ir.qgdev.qoqnus.dmlink.presentation.session;

import ir.qgdev.qoqnus.dmlink.application.session.SessionService;
import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import ir.qgdev.qoqnus.dmlink.domain.session.SessionRepository;
import ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.jwt.JwtService;
import ir.qgdev.qoqnus.dmlink.presentation.session.dto.SessionCreateRequestDto;
import ir.qgdev.qoqnus.dmlink.presentation.session.dto.SessionCreateResponseDto;
import ir.qgdev.qoqnus.dmlink.presentation.session.dto.SessionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/session")
public class SessionRestController {
    private final SessionRepository sessionRepository;
    private final SessionService sessionService;
    private final JwtService jwtService;

    @PostMapping("/")
    public ResponseEntity<SessionCreateResponseDto> create(@RequestBody SessionCreateRequestDto req) {
        Session session = sessionService.login(req.username(), req.password())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect username or password"));
        String token = jwtService.create(session.getId().toString());
        SessionCreateResponseDto res = new SessionCreateResponseDto(token, session);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public Collection<SessionDto> getAll() {
        return sessionRepository.findAll().stream()
                .map(SessionDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    public SessionDto get(@PathVariable("id") Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Session with given id not found"));
        return new SessionDto(session);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        sessionRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
