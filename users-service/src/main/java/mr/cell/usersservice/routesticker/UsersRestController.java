package mr.cell.usersservice.routesticker;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UsersRestController {
    private final UsersService usersService;

    public UsersRestController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public Collection<UserDTO> getAllUsers() {
        return usersService.getUsers().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable final String id) {
        final User user = usersService.getUser(UUID.fromString(id));
        return new UserDTO(user);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable final String id, @RequestBody final UserDTO user) {
        final User updatedUser = usersService.updateUser(UUID.fromString(id), user);
        return new UserDTO(updatedUser);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO saveUser(@RequestBody final UserDTO user) {
        final User savedUser = usersService.saveUser(user);
        return new UserDTO(savedUser);
    }

    @DeleteMapping("/{id}")
    public UserDTO deleteUser(@PathVariable final String id) {
        final User deletedUser = usersService.deleteUser(UUID.fromString(id));
        return new UserDTO(deletedUser);
    }
}
