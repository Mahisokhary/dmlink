package ir.qgdev.qoqnus.dmlink.presentation.action;

import ir.qgdev.qoqnus.dmlink.domain.action.Action;
import ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/action/@me")
public class ActionSelfRestController {
    private final AuthenticationService authenticationService;

    @GetMapping
    public Collection<String> get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationService.fetchPermittedActions(authentication).stream()
                .map(Action::getName)
                .toList();
    }

}
