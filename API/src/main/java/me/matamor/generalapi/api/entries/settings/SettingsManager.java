package me.matamor.generalapi.api.entries.settings;

import me.matamor.minesoundapi.storage.DataHandler;
import me.matamor.minesoundapi.settings.Settings;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SettingsManager {

    private final Map<Class<? extends Plugin>, Map<Class<? extends Settings>, Settings>> settings = new HashMap<>();

    private final DataHandler plugin;

    public SettingsManager(DataHandler plugin) {
        this.plugin = plugin;
    }

    public Map<Class<? extends Settings>, Settings> getPluginSettings(Class<? extends Plugin> clazz) {
        return this.settings.computeIfAbsent(clazz, k -> new HashMap<>());
    }

    public Collection<Settings> getSettings(Class<? extends Plugin> clazz) {
        return getPluginSettings(clazz).values();
    }

    public void registerSettings(Settings... values) {
        registerSettings(this.plugin.getPlugin().getClass(), values);
    }

    public void registerSettings(Class<? extends Plugin> clazz, Settings... entries) {
        Map<Class<? extends Settings>, Settings> pluginSettings = getPluginSettings(clazz);
        for (Settings settings : entries) pluginSettings.put(settings.getClass(), settings);
    }

    public <T extends Settings> T getSetting(Class<T> clazz) {
        return getSetting(this.plugin.getPlugin().getClass(), clazz);
    }

    public <T extends Settings> T getSetting(Class<? extends Plugin> pluginClazz, Class<T> clazz) {
        Settings setting = getPluginSettings(pluginClazz).get(clazz);
        return (setting == null ? null : (T) setting);
    }

    public Collection<Map<Class<? extends Settings>, Settings>> getSettings() {
        return Collections.unmodifiableCollection(this.settings.values());
    }

    public void reload() {
        for (Map<Class<? extends Settings>, Settings> entries : this.settings.values()) {
            for (Settings settings : entries.values()) {
                settings.reload();
            }
        }
    }
}
