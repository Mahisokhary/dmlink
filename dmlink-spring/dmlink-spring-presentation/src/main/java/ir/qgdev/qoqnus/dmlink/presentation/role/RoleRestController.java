package ir.qgdev.qoqnus.dmlink.presentation.role;

import ir.qgdev.qoqnus.dmlink.application.role.RoleService;
import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import ir.qgdev.qoqnus.dmlink.domain.role.RoleRepository;
import ir.qgdev.qoqnus.dmlink.presentation.role.dto.RoleCreateRequestDto;
import ir.qgdev.qoqnus.dmlink.presentation.role.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
public class RoleRestController {
    private final RoleService roleService;
    private final RoleRepository roleRepository;

    @PostMapping("/")
    public ResponseEntity<RoleDto> create(@RequestBody RoleCreateRequestDto req) {
        Collection<Role> parents = req.parents().stream()
                .map(roleRepository::findById)
                .map(o -> o
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid parents"))
                ).toList();
        Role role = roleService.create(req.name(), parents);
        return new ResponseEntity<>(new RoleDto(role), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public Collection<RoleDto> getAll() {
        return roleRepository.findAll().stream()
                .map(RoleDto::new)
                .toList();
    }

}
