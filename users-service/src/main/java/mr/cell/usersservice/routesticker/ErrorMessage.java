package mr.cell.usersservice.routesticker;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private final String message;
    private final String timestamp;
}
