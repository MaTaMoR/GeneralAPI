package me.matamor.generalapi.api.entries.settings;

import me.matamor.minesoundapi.settings.SimpleSettings;
import org.bukkit.plugin.Plugin;

public class SearchableSettings<T extends SearchableSetting> extends SimpleSettings<T> {

    public SearchableSettings(Plugin plugin, String path) {
        super(plugin, path);
    }

    public T search(String name) {
        for(T setting : getSettings()) if(setting.getName().equals(name)) return setting;
        return null;
    }
}
