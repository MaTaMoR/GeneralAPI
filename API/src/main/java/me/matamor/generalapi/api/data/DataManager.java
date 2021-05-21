package me.matamor.generalapi.api.data;

import me.matamor.generalapi.api.identifier.Identifier;
import me.matamor.generalapi.api.storage.DataStorage;
import me.matamor.generalapi.api.storage.StorageException;
import me.matamor.generalapi.api.utils.Callback;

import java.util.Collection;
import java.util.UUID;

public interface DataManager {

    DataStorage<UUID, Identifier> getStorage();

    PlayerData load(Identifier identifier);

    PlayerData load(Identifier identifier, boolean cache);

    void loadAsync(Identifier identifier, Callback<PlayerData> callback);

    void loadAsync(Identifier identifier, boolean cache, Callback<PlayerData> callback);

    void loadAll();

    PlayerData getData(UUID uuid);

    PlayerData getData(String name);

    void unload(UUID uuid);

    void unload(PlayerData playerData);

    void unloadAsync(UUID uuid);

    void unloadAsync(PlayerData playerData);

    void unloadAll() throws StorageException;

    Collection<PlayerData> getEntries();

}
