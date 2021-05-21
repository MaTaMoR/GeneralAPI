package me.matamor.generalapi.api.settings;

import me.matamor.minesoundapi.settings.defaults.IntegerSetting;
import org.bukkit.plugin.Plugin;

public class PluginSettings extends SimpleSettings<Setting> {

    public PluginSettings(Plugin plugin) {
        super(plugin, "config.yml");
    }

    public final Setting<Integer> SCOREBOARD_UPDATE_DELAY = register(new IntegerSetting("Configuration.ScoreboardUpdateDelay", 20));
}
