package me.matamor.generalapi.api.storage.database;

import me.matamor.minesoundapi.storage.StorageException;

public class DatabaseException extends StorageException {

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Exception exception) {
        super(message, exception);
    }
}
