package ir.qgdev.qoqnus.dmlink.presentation.action;

import ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/action/@me")
public class ActionSelfRestController {
}
