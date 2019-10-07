package mr.cell.usersservice;

import java.util.Collection;
import java.util.UUID;

public interface UsersService {
    User getUser(UUID id);
    Collection<User> getUsers();
    User deleteUser(UUID id);
    User saveUser(User user);
    User updateUser(UUID id, User user);
}
