package me.matamor.generalapi.api.scoreboard.board;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface BoardReceiver {

    UUID getUUID();

    Player getPlayer();

    String replace(String text);

}
