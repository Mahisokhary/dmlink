package ir.qgdev.qoqnus.dmlink.presentation.role;

import ir.qgdev.qoqnus.dmlink.application.role.RoleService;
import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import ir.qgdev.qoqnus.dmlink.domain.role.RoleRepository;
import ir.qgdev.qoqnus.dmlink.presentation.role.dto.RoleCreateRequestDto;
import ir.qgdev.qoqnus.dmlink.presentation.role.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleRestController {
    private final RoleService roleService;
    private final RoleRepository roleRepository;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDto create(@RequestBody RoleCreateRequestDto req) {
        Collection<Role> parents = req.parents().stream()
                .map(Long::parseLong)
                .map(roleRepository::findById)
                .map(o -> o
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid parents"))
                ).toList();
        Role role = roleService.create(req.name(), parents);
        return new RoleDto(role);
    }

    @GetMapping("/")
    public Collection<RoleDto> getAll() {
        return roleRepository.findAll().stream()
                .map(RoleDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    public RoleDto get(@PathVariable("id") Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role with given id not found"));
        return new RoleDto(role);
    }

    @GetMapping(value = "/", params = "name")
    public RoleDto getByName(@RequestParam("name") String name) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role with given name not found"));
        return new RoleDto(role);
    }

    @PutMapping("/{id}")
    public RoleDto modify(@PathVariable("id") Long id, @RequestBody RoleCreateRequestDto req) {
        Collection<Role> parents = req.parents().stream()
                .map(i -> {
                    try {
                        return Long.valueOf(i);
                    } catch (NumberFormatException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid parents", e);
                    }
                })
                .map(roleRepository::findById)
                .map(o -> o
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid parents"))
                ).toList();
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role with given id not found"));
        role.setName(req.name());
        role.setParents(parents);
        return new RoleDto(roleRepository.save(role));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        roleRepository.deleteById(id);
    }

}
