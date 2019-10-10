package mr.cell.routesticker.usersservice;

import java.util.Collection;
import java.util.UUID;

public interface UsersService {
    User getUser(UUID id);
    Collection<User> getUsers();
    User deleteUser(UUID id);
    User saveUser(UserDTO user);
    User updateUser(UUID id, UserDTO user);
}
