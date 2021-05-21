package me.matamor.generalapi.api.storage;

public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Exception exception) {
        super(message, exception);
    }
}
