package me.matamor.generalapi.api.utils.runnables;

import org.bukkit.scheduler.BukkitTask;

public interface SimpleRunnable {

    BukkitTask createTask();

    boolean isActive();

    void check();

    void start();

    void stop();

}
