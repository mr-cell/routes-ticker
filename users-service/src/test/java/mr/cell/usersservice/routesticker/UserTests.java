package mr.cell.usersservice.routesticker;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class UserTests {

    private UUID id;
    private User initialUser;

    @Before
    public void setup() {
        id = UUID.randomUUID();
        initialUser = new User(id, "username", "first", "last", false);
    }

    @Test
    public void testUpdateUsernameModified() {
        // given
        final UserDTO modifiedUser = new UserDTO(null, "username_modified", null, null, false);

        // when
        final User updatedUser = initialUser.update(modifiedUser);

        // then
        assertEquals(initialUser.getId(), updatedUser.getId());
        assertEquals(modifiedUser.getUsername(), updatedUser.getUsername());
        assertEquals(initialUser.getFirstName(), updatedUser.getFirstName());
        assertEquals(initialUser.getLastName(), updatedUser.getLastName());
    }

    @Test
    public void testUpdateFirstNameModified() {
        // given
        final UserDTO modifiedUser = new UserDTO(null, null, "first_modified", null, false);

        // when
        final User updatedUser = initialUser.update(modifiedUser);

        // then
        assertEquals(initialUser.getId(), updatedUser.getId());
        assertEquals(initialUser.getUsername(), updatedUser.getUsername());
        assertEquals(modifiedUser.getFirstName(), updatedUser.getFirstName());
        assertEquals(initialUser.getLastName(), updatedUser.getLastName());
    }

    @Test
    public void testUpdateLastNameModified() {
        // given
        final UserDTO modifiedUser = new UserDTO(null, null, null, "last_modified", false);

        // when
        final User updatedUser = initialUser.update(modifiedUser);

        // then
        assertEquals(initialUser.getId(), updatedUser.getId());
        assertEquals(initialUser.getUsername(), updatedUser.getUsername());
        assertEquals(initialUser.getFirstName(), updatedUser.getFirstName());
        assertEquals(modifiedUser.getLastName(), updatedUser.getLastName());
    }

    public void testUpdateIsAdminModified() {
        // given
        final UserDTO modifiedUser = new UserDTO(null, null, null, null, true);

        // when
        final User updatedUser = initialUser.update(modifiedUser);

        // then
        assertEquals(initialUser.getId(), updatedUser.getId());
        assertEquals(initialUser.getUsername(), updatedUser.getUsername());
        assertEquals(initialUser.getFirstName(), updatedUser.getFirstName());
        assertEquals(initialUser.getLastName(), updatedUser.getLastName());
    }
}
