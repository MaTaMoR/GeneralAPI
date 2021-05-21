package me.matamor.generalapi.api.settings;

import me.matamor.minesoundapi.config.IConfig;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

public interface Settings<T extends Setting> {

    Plugin getPlugin();

    IConfig getConfig();

    T register(T setting);

    void reload();

    Collection<T> getSettings();
}
