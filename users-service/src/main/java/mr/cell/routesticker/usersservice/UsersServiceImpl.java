package mr.cell.routesticker.usersservice;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersMongoRepository users;

    public UsersServiceImpl(UsersMongoRepository users) {
        this.users = users;
    }

    @Override
    public User getUser(final UUID id) {
        return users.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, User.class));
    }

    @Override
    public Collection<User> getUsers() {
        return users.findAll();
    }

    @Override
    public User deleteUser(final UUID id) {
        final User user = users.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, User.class));
        users.deleteById(id);
        return user;
    }

    @Override
    public User saveUser(final UserDTO user) {
        final UUID id = UUID.randomUUID();
        final User userToBeSaved = new User(id, null, null, null, false).update(user);
        users.save(userToBeSaved);
        return userToBeSaved;
    }

    @Override
    public User updateUser(final UUID id, final UserDTO user) {
        final User userToBeUpdated = users.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, User.class));
        final User updatedUser = userToBeUpdated.update(user);
        return users.save(updatedUser);
    }
}
