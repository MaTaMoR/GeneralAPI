package me.matamor.generalapi.api.data;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Getter;
import me.matamor.minesoundapi.MineSoundAPI;
import me.matamor.minesoundapi.identifier.Identifier;
import me.matamor.minesoundapi.scoreboard.reflection.IScoreboardManager;
import me.matamor.minesoundapi.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class PlayerEngine implements Listener {

    private final Cache<String, Boolean> connectedPlayers;

    @Getter
    private final MineSoundAPI plugin;

    public PlayerEngine(MineSoundAPI plugin) {
        this.plugin = plugin;

        this.connectedPlayers = CacheBuilder.newBuilder()
                .initialCapacity(100)
                .expireAfterWrite(1000, TimeUnit.MILLISECONDS)
                .build();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        if (this.plugin.isLoaded()) {
            if (event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
                String name = event.getName();

                if (this.connectedPlayers.getIfPresent(name) != null) {
                    event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Utils.color("&cYa te estas conectando al servidor!"));
                } else {
                    this.connectedPlayers.put(name, true);

                    try {
                        Identifier identifier = this.plugin.getIdentifierManager().load(event.getUniqueId(), name);
                        this.plugin.getDataManager().load(identifier);
                    } catch (Exception e) {
                        this.plugin.getLogger().log(Level.SEVERE, "There was an error while loading PlayerData (" + name + ", " + event.getUniqueId() + ")", e);
                        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Utils.color("&cHa ocurrido un error mientras se cargaba tu Informacion! Vuelvete a conectar."));

                        this.connectedPlayers.invalidate(name);
                    }
                }
            }
        } else {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Utils.color("&cEl servidor se esta cargando!"));
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.connectedPlayers.invalidate(event.getPlayer().getName());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        //Remove the board from the manager
        this.plugin.getBoardManager().unregister(player.getUniqueId());

        //Remove the Scoreboard instance of the player
        IScoreboardManager.removeScoreboard(player.getUniqueId());

        //Unload data
        if (this.plugin.isEnabled()) {
            this.plugin.getDataManager().unloadAsync(player.getUniqueId());
        } else {
            this.plugin.getDataManager().unload(player.getUniqueId());
        }

        this.plugin.getIdentifierManager().remove(player.getUniqueId());
    }
}
