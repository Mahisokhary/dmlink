package ir.qgdev.qoqnus.dmlink.presentation.action;

import ir.qgdev.qoqnus.dmlink.domain.action.Action;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;

public interface SpringAction extends Action, GrantedAuthority {
    @Override
    @NonNull
    default String getAuthority() {
        return getName();
    }

}
