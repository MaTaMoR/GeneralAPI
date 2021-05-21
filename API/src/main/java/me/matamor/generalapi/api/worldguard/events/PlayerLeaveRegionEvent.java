package me.matamor.generalapi.api.worldguard.events;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerLeaveRegionEvent extends PlayerRegionEvent {

    public PlayerLeaveRegionEvent(Player who, ProtectedRegion region, Location location, MoveCause cause) {
        super(who, region, location, cause);
    }
}
