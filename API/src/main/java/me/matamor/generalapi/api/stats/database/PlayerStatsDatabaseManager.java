package me.matamor.generalapi.api.stats.database;

import me.matamor.minesoundapi.identifier.Identifier;
import me.matamor.minesoundapi.storage.database.PlayerSimpleDatabaseManager;
import me.matamor.minesoundapi.stats.PlayerStats;
import me.matamor.minesoundapi.stats.SimpleStatsEntry;
import me.matamor.minesoundapi.stats.StatsEntry;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerStatsDatabaseManager extends PlayerSimpleDatabaseManager<PlayerStats> {

    public PlayerStatsDatabaseManager(Plugin plugin) {
        this(plugin, false);
    }

    public PlayerStatsDatabaseManager(Plugin plugin, boolean load) {
        super(plugin, "PlayerStats", 1.0, load);
    }

    @Override
    public void executeInsertQuery(PreparedStatement statement, PlayerStats data) throws SQLException {
        for (StatsEntry statsEntry : data.getValues()) {
            statement.setInt(1, data.getIdentifier().getId());
            statement.setString(2, statsEntry.getName());

            statement.setInt(3, statsEntry.getGames());
            statement.setInt(4, statsEntry.getWins());
            statement.setInt(5, statsEntry.getLoses());
            statement.setInt(6, statsEntry.getKills());
            statement.setInt(7, statsEntry.getDeaths());
            statement.setInt(8, statsEntry.getBrokenBlocks());
            statement.setInt(9, statsEntry.getPlacedBlocks());
            statement.setInt(10, statsEntry.getHits());
            statement.setInt(11, statsEntry.getShoots());
            statement.setInt(12, statsEntry.getArrowHits());
            statement.setInt(13, statsEntry.getWalkedBlocks());

            statement.setLong(14, statsEntry.getTimePlayed());

            statement.addBatch();
        }

        statement.executeBatch();
    }

    @Override
    public PlayerStats deserialize(ResultSet resultSet, Identifier identifier) throws SQLException {
        PlayerStats playerStats = new PlayerStats(getPlugin(), identifier, this);
        playerStats.add(deserialize(resultSet, playerStats));

        while (resultSet.next()) {
            playerStats.add(deserialize(resultSet, playerStats));
        }

        return playerStats;
    }

    private StatsEntry deserialize(ResultSet resultSet, PlayerStats playerStats) throws SQLException {
        StatsEntry statsEntry = new SimpleStatsEntry(playerStats, resultSet.getString("stats_name"));

        statsEntry.setGames(resultSet.getInt("games"));
        statsEntry.setWins(resultSet.getInt("wins"));
        statsEntry.setLoses(resultSet.getInt("loses"));
        statsEntry.setKills(resultSet.getInt("kills"));
        statsEntry.setDeaths(resultSet.getInt("deaths"));
        statsEntry.setBrokenBlocks(resultSet.getInt("broken_blocks"));
        statsEntry.setPlacedBlocks(resultSet.getInt("placed_blocks"));
        statsEntry.setHits(resultSet.getInt("hits"));
        statsEntry.setShoots(resultSet.getInt("shoots"));
        statsEntry.setArrowHits(resultSet.getInt("arrow_hits"));
        statsEntry.setWalkedBlocks(resultSet.getInt("walked_blocks"));

        statsEntry.setTimePlayed(resultSet.getLong("time_played"));

        return statsEntry;
    }
}
