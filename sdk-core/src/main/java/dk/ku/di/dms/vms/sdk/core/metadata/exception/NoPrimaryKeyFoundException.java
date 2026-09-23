package dk.ku.di.dms.vms.sdk.core.metadata.exception;

public class NoPrimaryKeyFoundException extends RuntimeException {
    public NoPrimaryKeyFoundException(String message) {
        super(message);
    }
}
