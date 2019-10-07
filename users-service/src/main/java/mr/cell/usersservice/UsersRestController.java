package mr.cell.usersservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersRestController {
    private final UsersService usersService;

    public UsersRestController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return usersService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable final String id) {
        return usersService.getUser(UUID.fromString(id));
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable final String id, @RequestBody final User user) {
        return usersService.updateUser(UUID.fromString(id), user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody final User user) {
        return usersService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable final String id) {
        return usersService.deleteUser(UUID.fromString(id));
    }
}
