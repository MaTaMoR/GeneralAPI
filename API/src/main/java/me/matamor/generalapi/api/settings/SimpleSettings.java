package me.matamor.generalapi.api.settings;

import me.matamor.minesoundapi.config.IConfig;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SimpleSettings<T extends Setting> implements Settings<T> {

    private final List<T> settings = new ArrayList<>();
    private final Plugin plugin;
    private final IConfig config;

    public SimpleSettings(Plugin plugin, String path) {
        this.plugin = plugin;
        this.config = new IConfig(plugin, path);
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public IConfig getConfig() {
        return config;
    }

    @Override
    public T register(T setting) {
        if(settings.contains(setting)) throw new RuntimeException("The setting " + setting.getPath() + " is already registered");

        settings.add(setting);

        setting.load(config);
        config.save();

        return setting;
    }

    @Override
    public void reload() {
        for(T setting : settings) setting.load(config);
    }

    @Override
    public Collection<T> getSettings() {
        return Collections.unmodifiableList(settings);
    }
}
