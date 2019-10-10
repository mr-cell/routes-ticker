package mr.cell.routesticker.usersservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Optional;
import java.util.UUID;

@Document
@TypeAlias("user")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @Id
    private final UUID id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final boolean isAdmin;

    @JsonCreator
    public User(
            @JsonProperty("id") final UUID id,
            @JsonProperty("username") final String username,
            @JsonProperty("firstName") final String firstName,
            @JsonProperty("lastName") final String lastName,
            @JsonProperty("isAdmin") final boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = isAdmin;
    }

    public User update(final UserDTO user) {
        final String username = Optional.ofNullable(user.getUsername()).orElse(this.username);
        final String firstName = Optional.ofNullable(user.getFirstName()).orElse(this.firstName);
        final String lastName = Optional.ofNullable(user.getLastName()).orElse(this.lastName);
        return new User(this.id, username, firstName, lastName, this.isAdmin);
    }
}
