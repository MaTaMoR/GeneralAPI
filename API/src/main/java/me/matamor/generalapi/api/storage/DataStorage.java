package me.matamor.generalapi.api.storage;

import me.matamor.minesoundapi.utils.RunnableQueue;
import org.bukkit.plugin.Plugin;

public interface DataStorage {

    Plugin getPlugin();

    RunnableQueue getRunnableQueue();

    default void runAsync(Runnable runnable) {
        getRunnableQueue().execute(runnable);
    }
}
