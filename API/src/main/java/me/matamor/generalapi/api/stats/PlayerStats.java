package me.matamor.generalapi.api.stats;

import me.matamor.minesoundapi.entries.defaults.ListDataEntry;
import me.matamor.minesoundapi.identifier.Identifier;
import me.matamor.minesoundapi.stats.database.PlayerStatsDatabaseManager;
import org.bukkit.plugin.Plugin;

public class PlayerStats extends ListDataEntry<StatsEntry> {

    public PlayerStats(Plugin plugin, Identifier identifier, PlayerStatsDatabaseManager databaseManager) {
        super(plugin, identifier, databaseManager);
    }

    @Override
    public StatsEntry get(String name) {
        StatsEntry statsEntry = super.get(name);

        if (statsEntry == null) {
            statsEntry = new SimpleStatsEntry(this, name);
            add(statsEntry);
        }

        return statsEntry;
    }
}
