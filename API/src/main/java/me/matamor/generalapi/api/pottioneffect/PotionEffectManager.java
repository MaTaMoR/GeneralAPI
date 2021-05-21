package me.matamor.generalapi.api.pottioneffect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.minesoundapi.MineSoundAPI;
import me.matamor.minesoundapi.utils.Utils;
import me.matamor.minesoundapi.utils.runnables.BasicRunnable;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionBrewer;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

@AllArgsConstructor
public class PotionEffectManager extends BasicRunnable {

    private final Map<UUID, Set<PotionEffectType>> potionEffects = new HashMap<>();

    @Getter
    private final MineSoundAPI plugin;

    public void update(Player player) {
        Set<PotionEffectType> potionEffects = this.potionEffects.computeIfAbsent(player.getUniqueId(), e -> new HashSet<>());

        Iterator<PotionEffectType> iterator = potionEffects.iterator();
        while (iterator.hasNext()) {
            PotionEffectType effectType = iterator.next();

            if (!player.hasPotionEffect(effectType)) {
                iterator.remove();

                Utils.callEvent(new PlayerRemovePotionEffectEvent(player, effectType));
            }
        }

        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            if (!potionEffects.contains(potionEffect.getType())) {
                potionEffects.add(potionEffect.getType());

                Utils.callEvent(new PlayerAddPotionEffectEvent(player, potionEffect.getType()));
            }
        }
    }

    @Override
    public BukkitTask createTask() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                getPlugin().getServer().getOnlinePlayers().forEach(e -> update(e));
            }
        }.runTaskTimer(this.plugin, 0, 20);
    }
}
