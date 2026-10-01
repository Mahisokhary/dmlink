package ir.qgdev.qoqnus.dmlink.presentation.role;

import ir.qgdev.qoqnus.dmlink.application.role.RoleService;
import ir.qgdev.qoqnus.dmlink.domain.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
public class RoleRestController {
    private final RoleService roleService;
    private final RoleRepository roleRepository;

}
