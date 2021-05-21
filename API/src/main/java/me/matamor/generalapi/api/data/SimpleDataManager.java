package me.matamor.generalapi.api.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.minesoundapi.entries.MultipleDataHandler;
import me.matamor.minesoundapi.identifier.Identifier;
import me.matamor.minesoundapi.storage.DataHandler;
import me.matamor.minesoundapi.storage.DataProvider;
import me.matamor.minesoundapi.storage.InstanceProvider;
import me.matamor.minesoundapi.storage.StorageException;
import me.matamor.minesoundapi.utils.Callback;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
public class SimpleDataManager implements DataManager {

    private final Map<UUID, PlayerData> entries = new ConcurrentHashMap<>();

    @Getter
    private final DataHandler handler;

    @Override
    public PlayerData load(Identifier identifier) {
        return load(identifier, true);
    }

    @Override
    public PlayerData load(Identifier identifier, boolean cache) {
        PlayerData playerData = this.entries.get(identifier.getUUID());

        if (playerData == null) {
            playerData = new SimplePlayerData(getHandler(), identifier);

            for (DataProvider dataProvider : getHandler().getDataEntries().getEntries()) {
                playerData.registerData(dataProvider);
            }

            for (Entry<Class<?>, InstanceProvider> entry : getHandler().getInstanceProviderManager().getEntries()) {
                playerData.registerInstance(entry.getKey(), entry.getValue().newInstance(playerData));
            }

            if (cache) {
                this.entries.put(identifier.getUUID(), playerData);
            }
        }

        return playerData;
    }

    @Override
    public void loadAsync(Identifier identifier, Callback<PlayerData> callback) {
        loadAsync(identifier, true, callback);
    }

    @Override
    public void loadAsync(Identifier identifier, boolean cache, final Callback<PlayerData> callback) {
        getHandler().getPlugin().getServer().getScheduler().runTaskAsynchronously(getHandler().getPlugin(), () -> {
            try {
                PlayerData playerData = load(identifier, cache);

                if (callback != null) {
                    callback.done(playerData, null);
                }
            } catch (StorageException e) {
                if (callback == null) {
                    e.printStackTrace();
                } else {
                    callback.done(null, e);
                }
            }
        });
    }

    @Override
    public synchronized void loadAll() {
        for (Player player : getHandler().getPlugin().getServer().getOnlinePlayers()) {
            load(getHandler().getIdentifierManager().load(player.getUniqueId(), player.getName()));
        }
    }

    @Override
    public PlayerData getData(UUID uuid) {
        return this.entries.get(uuid);
    }

    @Override
    public PlayerData getData(String name) {
        return this.entries.values().stream().filter(e -> e.getIdentifier().getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public void unload(UUID uuid) {
        unload(this.entries.get(uuid));
    }

    @Override
    public void unload(PlayerData playerData) {
        if (playerData == null) return;

        this.entries.remove(playerData.getIdentifier().getUUID());

        playerData.saveData();
    }

    @Override
    public void unloadAsync(UUID uuid) {
        unloadAsync(this.entries.get(uuid));
    }

    @Override
    public void unloadAsync(PlayerData playerData) {
        if (playerData == null) return;

        this.entries.remove(playerData.getIdentifier().getUUID());

        playerData.saveDataAsync();
    }

    @Override
    public synchronized void unloadAll() {
        this.entries.values().forEach(MultipleDataHandler::saveData);
        this.entries.clear();
    }

    @Override
    public Collection<PlayerData> getEntries() {
        return Collections.unmodifiableCollection(this.entries.values());
    }
}
