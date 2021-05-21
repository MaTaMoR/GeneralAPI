package me.matamor.generalapi.api.storage.database.settings;

import lombok.Getter;
import me.matamor.minesoundapi.MineSoundAPI;
import me.matamor.minesoundapi.config.IConfig;
import me.matamor.minesoundapi.utils.Validate;
import me.matamor.minesoundapi.utils.serializer.ConnectionSettingsSerializer;
import me.matamor.minesoundapi.utils.serializer.SerializationException;
import org.bukkit.configuration.ConfigurationSection;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

public class ConnectionSettingsManager {

    private static final String DEFAULT_SETTINGS = "Default";

    @Getter
    private final MineSoundAPI plugin;

    @Getter
    private final IConfig config;

    @Getter
    private final ConnectionSettingsSerializer serializer;

    @Getter
    private ConnectionSettings defaultSettings;


    private final Map<String, ConnectionSettings> entries = new LinkedHashMap<>();

    public ConnectionSettingsManager(MineSoundAPI plugin) {
        this.plugin = plugin;
        this.config = new IConfig(this.plugin, "connection_settings.yml");
        this.serializer = new ConnectionSettingsSerializer(this);
    }

    public boolean load() {
        this.defaultSettings = null;
        this.entries.clear();

        if (!this.config.exists()) {
            this.config.save();
        }

        //Check if Section exists
        ConfigurationSection configurationSection = this.config.getConfigurationSection("Connections");
        if (configurationSection == null || configurationSection.getKeys(false).isEmpty()) {
            this.plugin.getLogger().log(Level.INFO, "[ConnectionSettingsManager] No ConnectionSettings found!");
            return false;
        }

        Map<String, ConnectionSettings> loadedEntries = new LinkedHashMap<>();

        //Load all the ConnectionSettings in the config
        for (String key : configurationSection.getKeys(false)) {
            try {
                ConnectionSettings connectionSettings = this.serializer.deserialize(configurationSection.get(key));
                loadedEntries.put(connectionSettings.getName(), connectionSettings);
            } catch (SerializationException e) {
                this.plugin.getLogger().log(Level.SEVERE, "[ConnectionSettingsManager] Couldn't deserialize connection: " + key, e);
                return false;
            }
        }

        //Check if default settings are loaded
        ConnectionSettings defaultSettings = loadedEntries.get(DEFAULT_SETTINGS);

        if (defaultSettings == null) {
            this.plugin.getLogger().log(Level.SEVERE, "[ConnectionSettingsManager] Default connection settings aren't set!");
            return false;
        }

        ConnectionSettingsType[] settingsTypes = ConnectionSettingsType.values();

        //Check the default Settings has all the SettingsType
        for (ConnectionSettingsType settingsType : settingsTypes) {
            if (!defaultSettings.contains(settingsType)) {
                this.plugin.getLogger().log(Level.SEVERE, "[ConnectionSettingsManager] Default connection settings doesn't have setting '" + settingsType.getKey() + "'");
                return false;
            }
        }

        loadedEntries.remove(DEFAULT_SETTINGS);

        //Assign default setting
        this.defaultSettings = defaultSettings;

        //Put all the loaded settings to default value
        loadedEntries.forEach(this.entries::put);

        return true;
    }

    public ConnectionSettings getOrCreate(String key) {
        Validate.notNull(this.defaultSettings, "Can't create settings is default is not loaded!");

        ConnectionSettings connectionSettings = this.entries.get(key);

        if (connectionSettings == null) {
            connectionSettings = new ConnectionSettings(key, this);

            connectionSettings.set(ConnectionSettingsType.DATABASE, key);

            try {
                this.config.set("Connections." + key, this.serializer.serialize(connectionSettings));
                this.config.save();
            } catch (SerializationException e) {
                throw new RuntimeException(e);
            }

            this.entries.put(key, connectionSettings);
        }

        return connectionSettings;
    }
}
