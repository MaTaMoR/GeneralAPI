package me.matamor.generalapi.api.pottioneffect;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.potion.PotionEffectType;

public class PlayerAddPotionEffectEvent extends PlayerEvent {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    @Getter
    private final PotionEffectType potionEffect;

    public PlayerAddPotionEffectEvent(Player who, PotionEffectType potionEffect) {
        super(who);

        this.potionEffect = potionEffect;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}