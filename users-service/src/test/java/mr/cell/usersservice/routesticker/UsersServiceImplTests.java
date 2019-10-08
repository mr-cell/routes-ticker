package mr.cell.usersservice.routesticker;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

public class UsersServiceImplTests {

    @Mock
    private UsersMongoRepository users;

    private UsersServiceImpl usersService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        usersService = new UsersServiceImpl(users);
    }

    @Test
    public void testGetUser() {
        // given
        final UUID id = UUID.randomUUID();
        when(users.findById(any())).thenReturn(Optional.of(new User(id, "test", "test_first", "test_last", false)));

        // when
        final User user = usersService.getUser(id);

        // then
        assertEquals(id, user.getId());
        assertEquals("test", user.getUsername());
        assertEquals("test_first", user.getFirstName());
        assertEquals("test_last", user.getLastName());
    }

    @Test
    public void testGetUserWithNonexistentId() {
        // given
        final UUID id = UUID.randomUUID();
        when(users.findById(any())).thenReturn(Optional.empty());

        // when
        try {
            usersService.getUser(id);
        } catch (final ResourceNotFoundException ex) {
            // then
            assertEquals(id.toString(), ex.getResourceId());
            assertEquals(User.class.getSimpleName(), ex.getResourceClassSimpleName());
            assertThat(ex.getMessage(), containsString(id.toString()));
        }
    }

    @Test
    public void testGetAllUsers() {
        // given
        when(users.findAll()).thenReturn(generateRandomUsers(5));

        // when
        Collection<User> users = usersService.getUsers();

        // then
        assertThat(users, hasSize(5));
    }

    @Test
    public void testSaveUser() {
        // given
        final UserDTO userToBeSaved = new UserDTO(null, "test", "test", "test", false);
        when(users.save(any())).then(invocation -> invocation.getArgument(0));

        // when
        final User savedUser = usersService.saveUser(userToBeSaved);

        // then
        assertThat(savedUser.getId(), is(notNullValue()));
        assertEquals(userToBeSaved.getUsername(), savedUser.getUsername());
        assertEquals(userToBeSaved.getFirstName(), savedUser.getFirstName());
        assertEquals(userToBeSaved.getLastName(), savedUser.getLastName());
    }

    @Test
    public void testDeleteUser() {
        // given
        final UUID id = UUID.randomUUID();
        when(users.findById(any())).thenReturn(Optional.of(new User(id, "test", "test", "test", false)));

        // when
        final User deletedUser = usersService.deleteUser(id);

        // then
        assertEquals(id, deletedUser.getId());
        assertEquals("test", deletedUser.getUsername());
        assertEquals("test", deletedUser.getFirstName());
        assertEquals("test", deletedUser.getLastName());
    }

    @Test
    public void testDeleteUserWithNonexistentId() {
        // given
        final UUID id = UUID.randomUUID();
        when(users.findById(any())).thenReturn(Optional.empty());

        // when
        try {
            usersService.deleteUser(id);
        } catch (final ResourceNotFoundException ex) {
            // then
            assertEquals(id.toString(), ex.getResourceId());
            assertEquals(User.class.getSimpleName(), ex.getResourceClassSimpleName());
            assertThat(ex.getMessage(), containsString(id.toString()));
        }
    }

    @Test
    public void testUpdateUser() {
        // given
        final UUID id = UUID.randomUUID();
        final User user = new User(id, "test", "test", "test", false);
        when(users.findById(id)).thenReturn(Optional.of(user));
        when(users.save(any())).then(invocation -> invocation.getArgument(0));

        // when
        final User updatedUser = usersService.updateUser(id,
                new UserDTO(null, "test_modified", "first_modified", "last_modified", false));

        // then
        assertEquals(id, updatedUser.getId());
        assertEquals("test_modified", updatedUser.getUsername());
        assertEquals("first_modified", updatedUser.getFirstName());
        assertEquals("last_modified", updatedUser.getLastName());
    }

    @Test
    public void tetUpdateUserWithNonexistentId() {
        final UUID id = UUID.randomUUID();
        when(users.findById(any())).thenReturn(Optional.empty());

        // when
        try {
            usersService.updateUser(id, new UserDTO(null, "test", "test", "test", false));
        } catch (final ResourceNotFoundException ex) {
            // then
            assertEquals(id.toString(), ex.getResourceId());
            assertEquals(User.class.getSimpleName(), ex.getResourceClassSimpleName());
            assertThat(ex.getMessage(), containsString(id.toString()));
        }
    }

    private List<User> generateRandomUsers(final int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> generateRandomUser())
                .collect(Collectors.toList());
    }

    private User generateRandomUser() {
        final UUID id = UUID.randomUUID();
        final String username = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        final String lastName = RandomStringUtils.randomAlphabetic(10);
        return new User(id, username, firstName, lastName, false);
    }
}
