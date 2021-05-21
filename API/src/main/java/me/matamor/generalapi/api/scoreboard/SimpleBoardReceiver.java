package me.matamor.generalapi.api.scoreboard;

import me.matamor.minesoundapi.scoreboard.board.BoardReceiver;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.util.UUID;

public abstract class SimpleBoardReceiver implements BoardReceiver {

    private final WeakReference<Player> player;
    private final UUID uuid;

    public SimpleBoardReceiver(Player player) {
        this.player = new WeakReference<>(player);
        this.uuid = player.getUniqueId();
    }

    @Override
    public Player getPlayer() {
        return this.player.get();
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }
}
