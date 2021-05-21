package me.matamor.generalapi.api.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.minesoundapi.entries.DataEntry;
import me.matamor.minesoundapi.entries.RegisteredData;
import me.matamor.minesoundapi.identifier.Identifier;
import me.matamor.minesoundapi.storage.DataHandler;
import me.matamor.minesoundapi.storage.DataProvider;
import me.matamor.minesoundapi.storage.StorageException;
import me.matamor.minesoundapi.utils.Validate;
import me.matamor.minesoundapi.utils.variables.VariableHandlersManager;
import org.bukkit.entity.Player;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
public class SimplePlayerData implements PlayerData {

    private final Map<Class<?>, RegisteredData> dataEntries = new ConcurrentHashMap<>();
    private final Map<Class<?>, Object> instances = new ConcurrentHashMap<>();
    private final Map<String, Object> storedData = new ConcurrentHashMap<>();

    @Getter
    private final DataHandler handler;

    @Getter
    private final Identifier identifier;

    @Override
    public UUID getUUID() {
        return this.identifier.getUUID();
    }

    @Override
    public Player getPlayer() {
        return this.identifier.getPlayer();
    }

    @Override
    public String replace(String string) {
        return VariableHandlersManager.PLAYER_VARIABLES.replace(this, string);
    }

    @Override
    public <T extends DataEntry> T registerData(@NotNull DataProvider<T> dataProvider) {
        Validate.isFalse(this.dataEntries.containsKey(dataProvider.getDataClass()), "The DataEntry " + dataProvider.getDataClass().getName() + " is already registered");

        T data = dataProvider.getStorage().load(this.identifier);
        if (data == null) {
            data = dataProvider.newInstance(this);
        }

        this.dataEntries.put(dataProvider.getDataClass(), new RegisteredData(this, data, dataProvider));

        return data;
    }

    @Override
    public Object registerInstance(@NotNull Class<?> clazz, @NotNull Object instance) {
        Validate.isTrue(clazz.isAssignableFrom(instance.getClass()), "The provided object isn't instance of the class '" + clazz.getName() + "'");
        Validate.isFalse(this.instances.containsKey(clazz), "The Class " + clazz.getName() + " is already registered");

        this.instances.put(clazz, instance);

        return instance;
    }

    @Override
    public <T> T setStoredData(String key, T data) {
        Validate.isFalse(this.storedData.containsKey(key), "Data already stored with the key " + key);

        this.storedData.put(key, data);

        return data;
    }

    @Override
    public boolean hasData(@NotNull Class<?> clazz) {
        return this.dataEntries.containsKey(clazz);
    }

    @Override
    public boolean hasInstance(@NotNull Class<?> clazz) {
        return this.instances.containsKey(clazz);
    }

    @Override
    public boolean hasStoredData(String key) {
        return this.storedData.containsKey(key);
    }

    @Override
    public <T extends DataEntry> T getData(@NotNull Class<T> clazz) {
        RegisteredData data = this.dataEntries.get(clazz);
        if (data == null) {
            return null;
        } else if (clazz.isAssignableFrom(data.getDataEntry().getClass())) {
            return (T) data.getDataEntry();
        } else {
            throw new RuntimeException("The Data " + data.getDataEntry().getClass().getName() + " isn't instance of " + clazz.getName());
        }
    }

    @Override
    public <T> T getInstance(@NotNull Class<T> clazz) {
        Object data = this.instances.get(clazz);
        if (data == null) {
            return null;
        } else if (clazz.isAssignableFrom(data.getClass())) {
            return (T) data;
        } else {
            throw new RuntimeException("The Object " + data.getClass().getName() + " isn't instance of " + clazz.getName());
        }
    }

    @Override
    public Object getStoredData(String key) {
        return this.storedData.get(key);
    }

    @Override
    public <T> T getStoredData(String key, Class<T> clazz) {
        Object data = this.storedData.get(key);
        if (data == null) {
            return null;
        } else if (clazz.isAssignableFrom(data.getClass())) {
            return (T) data;
        } else {
            throw new RuntimeException("The Object " + data.getClass().getName() + " isn't instance of " + clazz.getName());
        }
    }

    @Override
    public RegisteredData getRegisteredData(@NotNull Class<?> clazz) {
        return this.dataEntries.get(clazz);
    }

    @Override
    public void unregister(@NotNull Class<?> clazz) {
        RegisteredData registeredData = this.dataEntries.get(clazz);
        if (registeredData == null) return;

        registeredData.save();

        this.dataEntries.remove(clazz);
    }

    @Override
    public void unregisterInstance(Class<?> clazz) {
        this.instances.remove(clazz);
    }

    @Override
    public void removeStoredData(String key) {
        this.storedData.remove(key);
    }

    @Override
    public void removeData(@NotNull Class<?> clazz) {
        this.dataEntries.remove(clazz);
    }

    @Override
    public void purgeData(@NotNull Class<?> clazz) {
        RegisteredData dataEntry = this.dataEntries.get(clazz);
        if (dataEntry == null) return;

        this.dataEntries.remove(clazz);

        try {
            dataEntry.getDataProvider().getStorage().remove(getIdentifier());
        } catch (StorageException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveData() throws StorageException {
        for (RegisteredData registeredData : this.dataEntries.values()) {
            registeredData.save();
        }
    }

    @Override
    public void saveDataAsync() {
        this.dataEntries.values().forEach(RegisteredData::saveAsync);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object instanceof PlayerData) {
            PlayerData other = (PlayerData) object;
            return Objects.equals(getIdentifier(), other.getIdentifier());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getIdentifier());
    }
}
