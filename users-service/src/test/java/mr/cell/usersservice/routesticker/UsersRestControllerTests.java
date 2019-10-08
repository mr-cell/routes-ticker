package mr.cell.usersservice.routesticker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@RunWith(SpringRunner.class)
@WebMvcTest
public class UsersRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService users;

    private User createUser(final String username) {
        return createUser(username, username + "_first", username + "_last");
    }

    private User createUser(final String username, final String firstName, final String lastName) {
        final UUID id = UUID.randomUUID();
        return new User(id, username, firstName, lastName, false);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // given
        final Map<UUID, User> usersMap = Stream.of("user1", "user2", "user3", "user4", "user5")
                .map(this::createUser)
                .collect(Collectors.toMap(User::getId, user -> user));
        when(users.getUsers()).thenReturn(usersMap.values());

        // when
        mockMvc.perform(get("/users").accept(APPLICATION_JSON_UTF8))
                // then
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$..id", hasSize(usersMap.size())))
                .andExpect(jsonPath("$..username", hasSize(usersMap.size())))
                .andExpect(jsonPath("$..firstName", hasSize(usersMap.size())))
                .andExpect(jsonPath("$..lastName", hasSize(usersMap.size())))
                .andExpect(jsonPath("$..isAdmin", hasSize(usersMap.size())));
    }

    @Test
    public void testGetUserById() throws Exception {
        // given
        final UUID id = UUID.randomUUID();
        final User user = new User(id, "user", "user_first", "user_last", false);
        when(users.getUser(any())).thenReturn(user);

        // when
        mockMvc.perform(get("/users/" + id).accept(APPLICATION_JSON_UTF8))
                // then
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(id.toString())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.isAdmin", is(user.isAdmin())));
    }

    @Test
    public void testGetUserByNonexistentId() throws Exception {
        // given
        final UUID id = UUID.randomUUID();
        when(users.getUser(id)).thenThrow(new ResourceNotFoundException(id, User.class));

        // when
        mockMvc.perform(get("/users/" + id).accept(APPLICATION_JSON_UTF8))
                // then
                .andExpect(status().isNotFound())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", containsString(id.toString())))
                .andExpect(jsonPath("$.timestamp", is(notNullValue())));
    }

    @Test
    public void testGetUserByInvalidId() throws Exception {
        // given
        // when (used id that does not conform to UUID format)
        mockMvc.perform(get("/users/1").accept(APPLICATION_JSON_UTF8))
                // then
                .andExpect(status().isBadRequest())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.timestamp", is(notNullValue())));
    }

    @Test
    public void testSaveUser() throws Exception {
        // given
        when(users.saveUser(any())).then(invocation -> {
            final UserDTO user = invocation.getArgument(0);
            final User newUser = new User(UUID.randomUUID(), null, null, null, false);
            return newUser.update(user);
        });
        final String userPayloadInJson = "{\"username\": \"user\", \"firstName\": \"user_first\", " +
                "\"lastName\": \"user_last\"}";

        // when
        mockMvc.perform(post("/users")
                .accept(APPLICATION_JSON_UTF8)
                .contentType(APPLICATION_JSON_UTF8)
                .content(userPayloadInJson)
        )
                // then
                .andExpect(status().isCreated())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.username", is("user")))
                .andExpect(jsonPath("$.firstName", is("user_first")))
                .andExpect(jsonPath("$.lastName", is("user_last")))
                .andExpect(jsonPath("$.isAdmin", is(false)));

    }

    @Test
    public void testUpdateUser() throws Exception {
        // given
        final UUID id = UUID.randomUUID();
        final User user = new User(id, "user", "user_first", "user_last", false);
        when(users.updateUser(any(), any())).then(invocation -> {
            final UserDTO modifiedUser = invocation.getArgument(1);
            return user.update(modifiedUser);
        });
        final String userPayloadInJson = "{\"username\": \"user_modified\"}";

        // when
        mockMvc.perform(put("/users/" + id)
                .accept(APPLICATION_JSON_UTF8)
                .contentType(APPLICATION_JSON_UTF8)
                .content(userPayloadInJson)
        )
                // then
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(id.toString())))
                .andExpect(jsonPath("$.username", is("user_modified")))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.isAdmin", is(user.isAdmin())));
    }

    @Test
    public void testUpdateUserWithNonexistentId() throws Exception {
        // given
        final UUID id = UUID.randomUUID();
        when(users.updateUser(any(), any())).thenThrow(new ResourceNotFoundException(id, User.class));
        final String userPayloadInJson = "{\"username\": \"user_modified\"}";

        // when
        mockMvc.perform(put("/users/" + id)
                .accept(APPLICATION_JSON_UTF8)
                .contentType(APPLICATION_JSON_UTF8)
                .content(userPayloadInJson)
        )
                // then
                .andExpect(status().isNotFound())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", containsString(id.toString())));
    }

    @Test
    public void testDeleteUser() throws Exception {
        // given
        final UUID id = UUID.randomUUID();
        final User user = new User(id, "user", "user_first", "user_last", false);
        when(users.deleteUser(id)).thenReturn(user).thenThrow(new ResourceNotFoundException(id, User.class));

        // when
        mockMvc.perform(delete("/users/" + id).accept(APPLICATION_JSON_UTF8))
                // then
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(user.getId().toString())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.isAdmin", is(user.isAdmin())));

        mockMvc.perform(delete("/users/" + id).accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", containsString(id.toString())))
                .andExpect(jsonPath("$.timestamp", is(notNullValue())));
    }
}
