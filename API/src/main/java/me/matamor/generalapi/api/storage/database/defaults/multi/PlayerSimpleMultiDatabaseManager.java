package me.matamor.generalapi.api.storage.database.defaults.multi;

import me.matamor.minesoundapi.identifier.Identifier;
import org.bukkit.plugin.Plugin;

public abstract class PlayerSimpleMultiDatabaseManager<V> extends SimpleMultiDatabaseManager<Identifier, V> {

    public PlayerSimpleMultiDatabaseManager(Plugin plugin, String name, double queriesVersion) {
        super(plugin, name, queriesVersion);
    }

    public PlayerSimpleMultiDatabaseManager(Plugin plugin, String name, double queriesVersion, boolean open) {
        super(plugin, name, queriesVersion, open);
    }
}
