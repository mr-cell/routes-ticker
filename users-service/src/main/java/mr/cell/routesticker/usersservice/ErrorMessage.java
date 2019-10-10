package mr.cell.routesticker.usersservice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private final String message;
    private final String timestamp;
}
