package ir.qgdev.qoqnus.dmlink.presentation.security.authenticate;

import ir.qgdev.qoqnus.dmlink.domain.action.Action;
import org.springframework.security.core.Authentication;

import java.util.Collection;

public interface AuthenticationService {
    Collection<Action> fetchPermittedActions(Authentication authentication);

}
