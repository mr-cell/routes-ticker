package mr.cell.usersservice.routesticker;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Optional;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @Id
    private final UUID id;
    private final String username;
    private final String firstName;
    private final String lastName;

    @JsonCreator
    public User(
            @JsonProperty("id") final UUID id,
            @JsonProperty("username") final String username,
            @JsonProperty("firstName") final String firstName,
            @JsonProperty("lastName") final String lastName) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User update(final User user) {
        final String username = Optional.ofNullable(user.getUsername()).orElse(this.username);
        final String firstName = Optional.ofNullable(user.getFirstName()).orElse(this.firstName);
        final String lastName = Optional.ofNullable(user.getLastName()).orElse(this.lastName);
        return new User(this.id, username, firstName, lastName);
    }
}
