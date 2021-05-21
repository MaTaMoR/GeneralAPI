package me.matamor.generalapi.api.worldguard.events;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerRegionEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final ProtectedRegion region;
    private final MoveCause cause;
    private final Location location;

    private boolean cancelled;

    public PlayerRegionEvent(Player who, ProtectedRegion region, Location location, MoveCause cause) {
        super(who);

        this.region = region;
        this.cause = cause;
        this.location = location;
    }

    public ProtectedRegion getRegion() {
        return region;
    }

    public MoveCause getCause() {
        return cause;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
