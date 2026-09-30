package ir.qgdev.qoqnus.dmlink.presentation.link;

import ir.qgdev.qoqnus.dmlink.application.link.LinkService;
import ir.qgdev.qoqnus.dmlink.domain.link.Link;
import ir.qgdev.qoqnus.dmlink.domain.link.LinkRepository;
import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import ir.qgdev.qoqnus.dmlink.domain.user.User;
import ir.qgdev.qoqnus.dmlink.presentation.link.dto.LinkCreateRequestDto;
import ir.qgdev.qoqnus.dmlink.presentation.link.dto.LinkDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/link")
public class LinkRestController {
    private final LinkService linkService;
    private final LinkRepository linkRepository;

    @PostMapping("/")
    public ResponseEntity<LinkDto> create(@RequestBody LinkCreateRequestDto req, @AuthenticationPrincipal Session session) {
        User owner = session != null ? session.getUser() : null;
        Link link = linkService.create(req.name(), req.target(), owner);
        return new ResponseEntity<>(new LinkDto(link), HttpStatus.CREATED);
    }

}
