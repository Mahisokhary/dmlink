package ir.qgdev.qoqnus.dmlink.presentation.user;

import ir.qgdev.qoqnus.dmlink.application.user.UserService;
import ir.qgdev.qoqnus.dmlink.domain.user.User;
import ir.qgdev.qoqnus.dmlink.domain.user.UserRepository;
import ir.qgdev.qoqnus.dmlink.presentation.user.dto.UserCreateRequestDto;
import ir.qgdev.qoqnus.dmlink.presentation.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserRestController {
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> create(@RequestBody UserCreateRequestDto req) {
        if (userRepository.existsByUsername(req.username())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        User user = userService.create(req.username(), req.displayName(), req.password());
        return new ResponseEntity<>(new UserDto(user), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public Collection<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserDto::new)
                .toList();
    }

}
