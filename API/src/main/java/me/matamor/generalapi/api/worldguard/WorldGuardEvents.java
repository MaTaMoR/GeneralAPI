package me.matamor.generalapi.api.worldguard;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.matamor.minesoundapi.utils.Utils;
import me.matamor.minesoundapi.worldguard.events.MoveCause;
import me.matamor.minesoundapi.worldguard.events.PlayerEnterRegionEvent;
import me.matamor.minesoundapi.worldguard.events.PlayerLeaveRegionEvent;
import me.matamor.minesoundapi.worldguard.events.PlayerRegionEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WorldGuardEvents implements Listener {

    private final Map<UUID, Set<ProtectedRegion>> regions = new ConcurrentHashMap<>();

    private final WorldGuardPlugin worldGuard;

    public WorldGuardEvents(WorldGuardPlugin worldGuard) {
        this.worldGuard = worldGuard;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        updateRegions(event.getPlayer(), MoveCause.CONNECT, event.getPlayer().getLocation(), event);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent event) {
        updateRegions(event.getPlayer(), MoveCause.DISCONNECT, event.getPlayer().getLocation(), event);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMove(PlayerMoveEvent event) {
        if(event.isCancelled()) return;

        Location from = event.getFrom();
        Location to = event.getTo();

        if(from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ()) return;

        updateRegions(event.getPlayer(), MoveCause.MOVE, to, event);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTeleport(PlayerTeleportEvent event) {
        if(event.isCancelled()) return;

        updateRegions(event.getPlayer(), MoveCause.TELEPORT, event.getTo(), event);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRespawn(PlayerRespawnEvent event) {
        updateRegions(event.getPlayer(), MoveCause.SPAWN, event.getRespawnLocation(), event);
    }

    private synchronized void updateRegions(Player player, MoveCause cause, Location location, PlayerEvent event) {
        Set<ProtectedRegion> regions = this.regions.computeIfAbsent(player.getUniqueId(), k -> new HashSet<>());

        if(cause == MoveCause.DISCONNECT) {
            regions.forEach(r -> callEvent(new PlayerLeaveRegionEvent(player, r, location, cause), event));

            this.regions.remove(player.getUniqueId());
        } else {
            Set<ProtectedRegion> newRegions = new HashSet<>();

            RegionManager regionManager = worldGuard.getRegionManager(location.getWorld());
            if (regionManager == null) {
                regions.forEach(r -> callEvent(new PlayerLeaveRegionEvent(player, r, location, cause), event));

                this.regions.put(player.getUniqueId(), new HashSet<>());
            } else {
                for (ProtectedRegion region : regionManager.getApplicableRegions(location).getRegions()) {
                    newRegions.add(region);

                    if (!regions.contains(region)) {
                        callEvent(new PlayerEnterRegionEvent(player, region, location, cause), event);
                    }
                }

                Iterator<ProtectedRegion> iterator = regions.iterator();
                while (iterator.hasNext()) {
                    ProtectedRegion region = iterator.next();

                    if (!newRegions.contains(region)) {
                        iterator.remove();
                        callEvent(new PlayerLeaveRegionEvent(player, region, location, cause), event);
                    }
                }

                this.regions.put(player.getUniqueId(), newRegions);
            }
        }
    }

    private void callEvent(PlayerRegionEvent event, PlayerEvent playerEvent) {
        if(Utils.callEvent(event).isCancelled() && playerEvent instanceof Cancellable) {
            ((Cancellable) playerEvent).setCancelled(true);
        }
    }
}
