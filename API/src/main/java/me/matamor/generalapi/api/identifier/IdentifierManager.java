package me.matamor.generalapi.api.identifier;

import me.matamor.generalapi.api.storage.StorageException;

import java.util.Collection;
import java.util.UUID;

public interface IdentifierManager {

    Identifier load(UUID uuid, String name);

    void loadAll() throws StorageException;

    Identifier getIdentifier(UUID uuid);

    Collection<Identifier> getIdentifiers();

    Identifier remove(UUID uuid);

    void unloadAll();

}
