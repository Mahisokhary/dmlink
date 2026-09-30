package ir.qgdev.qoqnus.dmlink.presentation.link;

import ir.qgdev.qoqnus.dmlink.application.link.LinkService;
import ir.qgdev.qoqnus.dmlink.domain.link.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/link")
public class LinkRestController {
    private final LinkService linkService;
    private final LinkRepository linkRepository;

}
