package ir.qgdev.qoqnus.dmlink.presentation.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/@me")
public class UserSelfRestController {
}
