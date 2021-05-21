package me.matamor.generalapi.bukkit.bossbar;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BossBarEngine implements Listener {

    private final Plugin plugin;

    public BossBarEngine(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        BossBarAPI.removeBossBar(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onTeleport(PlayerTeleportEvent event) {
        handlePlayerTeleport(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRespawn(PlayerRespawnEvent event) {
        handlePlayerTeleport(event.getPlayer());
    }

    protected void handlePlayerTeleport(Player player) {
        final BossBar bar = BossBarAPI.getBossBar(player);
        if (bar == null) return;

        bar.setVisible(false);

        new BukkitRunnable() {
            @Override
            public void run() {
                bar.setVisible(true);
            }
        }.runTaskLater(plugin, 2);
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent event) {
        final BossBar bossBar = BossBarAPI.getBossBar(event.getPlayer());
        if (bossBar == null) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (event.getPlayer().isOnline()) {
                    bossBar.updateMovement();
                }
            }
        }.runTaskLater(plugin, 0);
    }
}
