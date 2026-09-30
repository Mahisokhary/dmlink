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
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

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

    @GetMapping("/")
    public Collection<LinkDto> getAll() {
        return linkRepository.findAll().stream()
                .map(LinkDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    public LinkDto get(@PathVariable("id") Long id) {
        Link link = linkRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Link with given id not found"));
        return new LinkDto(link);
    }

    @GetMapping(value = "/", params = "name")
    public LinkDto getByName(@RequestParam("name") String name) {
        Link link = linkRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Link with given name not found"));
        return new LinkDto(link);
    }

    @PutMapping("/{id}")
    public LinkDto modify(@PathVariable("id") Long id, @RequestBody LinkCreateRequestDto req) {
        Link link = linkRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Link with given id not found"));
        link.setName(req.name());
        link.setTarget(req.target());
        return new LinkDto(linkRepository.save(link));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        linkRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
