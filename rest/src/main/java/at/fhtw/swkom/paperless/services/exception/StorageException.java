package at.fhtw.swkom.paperless.services.exception;

public class StorageException extends RuntimeException {
    public StorageException(String message) {
        super(message);
    }
}
