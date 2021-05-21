package me.matamor.generalapi.api.utils.serializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.custominventories.utils.CastUtils;
import me.matamor.minesoundapi.storage.database.settings.ConnectionSettings;
import me.matamor.minesoundapi.storage.database.settings.ConnectionSettingsManager;
import me.matamor.minesoundapi.storage.database.settings.ConnectionSettingsType;

import java.util.LinkedHashMap;
import java.util.Map;

@AllArgsConstructor
public class ConnectionSettingsSerializer implements Serializer<ConnectionSettings> {

    @Getter
    private final ConnectionSettingsManager settingsManager;

    @Override
    public Map<String, Object> serialize(ConnectionSettings value) throws SerializationException {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("Name", value.getName());

        for (Map.Entry<ConnectionSettingsType, Object> entry : value.getSettings().entrySet()) {
            map.put(entry.getKey().getKey(), entry.getValue());
        }

        return map;
    }

    @Override
    public ConnectionSettings deserialize(Object serialized) throws SerializationException {
        Map<String, Object> map = asMap(serialized);

        String name;

        try {
            name = CastUtils.asString(get(map, "Name"));
        } catch (CastUtils.FormatException e) {
            throw new SerializationException("Invalid or missing Name!");
        }

        map.remove("Name");

        Map<ConnectionSettingsType, Object> defaultSettings = new LinkedHashMap<>();

        for (ConnectionSettingsType settingsType : ConnectionSettingsType.values()) {
            defaultSettings.put(settingsType, map.get(settingsType.getKey()));
        }

        return new ConnectionSettings(name, this.settingsManager, defaultSettings);
    }
}
