package me.matamor.generalapi.bukkit.bossbar;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface BossBar {

    String getMessage();

    void setVisible(boolean flag);

    boolean isVisible();

    float getMaxHealth();

    void setHealth(float percentage);

    float getHealth();

    void setMessage(String message);

    Player getReceiver();

    Location getLocation();

    void updateMovement();

}
