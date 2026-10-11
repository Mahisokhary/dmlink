package ir.qgdev.qoqnus.dmlink.presentation.user;

import ir.qgdev.qoqnus.dmlink.application.user.UserService;
import ir.qgdev.qoqnus.dmlink.domain.user.User;
import ir.qgdev.qoqnus.dmlink.domain.user.UserRepository;
import ir.qgdev.qoqnus.dmlink.presentation.user.dto.UserCreateRequestDto;
import ir.qgdev.qoqnus.dmlink.presentation.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserRestController {
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserCreateRequestDto req) {
        if (userRepository.existsByUsername(req.username())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        User user = userService.create(req.username(), req.displayName(), req.password());
        return new UserDto(user);
    }

    @GetMapping("/")
    public Collection<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable("id") Long id) {
        return userRepository.findById(id)
                .map(UserDto::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with given id not found"));
    }

    @GetMapping(value = "/", params = "username")
    public UserDto getByUsername(@RequestParam("username") String username) {
        return userRepository.findByUsername(username)
                .map(UserDto::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with given username not found"));
    }

    @PutMapping("/{id}")
    public UserDto modify(@PathVariable("id") Long id, @RequestBody UserCreateRequestDto req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with given id not found"));
        user.setUsername(req.username());
        user.setDisplayName(req.displayName());
        userService.changePassword(user, req.password());
        return new UserDto(userRepository.save(user));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
    }

}
