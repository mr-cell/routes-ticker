package mr.cell.routesticker.usersservice;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {

    private final String resourceId;
    private final String resourceClassSimpleName;

    public <T> ResourceNotFoundException(final UUID uuid, final Class<T> resourceClass) {
        this(uuid.toString(), resourceClass);
    }

    public <T> ResourceNotFoundException(final String id, final Class<T> resourceClass) {
        super("Resource not found [class='" + resourceClass.getName() + "', id='" + id + "']");
        this.resourceId = id;
        this.resourceClassSimpleName = resourceClass.getSimpleName();
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getResourceClassSimpleName() {
        return resourceClassSimpleName;
    }
}
