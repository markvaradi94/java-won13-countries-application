package ro.fasttrackit.countries.application.exception;

public class ResourceNotFoundException extends RuntimeException {
    private final long resourceId;

    public ResourceNotFoundException(String message, long resourceId) {
        super(message);
        this.resourceId = resourceId;
    }

    public long getResourceId() {
        return resourceId;
    }
}
