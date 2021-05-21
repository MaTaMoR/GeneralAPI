package me.matamor.generalapi.api.utils.runnables;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public abstract class BasicRunnable implements SimpleRunnable {

    protected BukkitTask task;
    protected int time;

    @Override
    public boolean isActive() {
        return this.task != null && (Bukkit.getScheduler().isQueued(this.task.getTaskId()) || Bukkit.getScheduler().isCurrentlyRunning(this.task.getTaskId()));
    }

    @Override
    public void check() {
        if (!isActive()) {
            start();
        }
    }

    @Override
    public void start() {
        if(isActive()) {
            stop();
        }

        this.task = createTask();
    }

    @Override
    public void stop() {
        if (isActive()) {
            this.task.cancel();
            this.task = null;
        }
    }
}
