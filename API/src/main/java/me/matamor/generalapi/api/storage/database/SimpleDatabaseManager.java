package me.matamor.generalapi.api.storage.database;

import lombok.Getter;
import me.matamor.minesoundapi.storage.database.queries.Queries;
import me.matamor.minesoundapi.storage.database.queries.SimpleQueries;
import me.matamor.minesoundapi.utils.RunnableQueue;
import org.bukkit.plugin.Plugin;

public abstract class SimpleDatabaseManager implements DatabaseManager {

    @Getter
    private final Plugin plugin;

    @Getter
    private final String name;

    @Getter
    private final ConnectionHandler connectionHandler;

    @Getter
    private final Queries queries;

    @Getter
    private final RunnableQueue runnableQueue;

    public SimpleDatabaseManager(Plugin plugin, String name, double queriesVersion) {
        this(plugin, name, queriesVersion, false);
    }

    public SimpleDatabaseManager(Plugin plugin, String name, double queriesVersion, boolean open) {
        this.plugin = plugin;
        this.name = name;
        this.connectionHandler = new ConnectionHandler(this);

        this.queries = new SimpleQueries(this, queriesVersion);
        this.queries.load();

        this.runnableQueue = new RunnableQueue();

        if (open) {
            loadDatabase();
        }
    }
}
