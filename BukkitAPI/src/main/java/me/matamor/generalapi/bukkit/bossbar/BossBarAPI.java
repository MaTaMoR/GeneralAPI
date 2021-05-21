package me.matamor.generalapi.bukkit.bossbar;

import org.bukkit.entity.Player;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class BossBarAPI {

    protected static final Map<UUID, BossBar> entries = new ConcurrentHashMap<>();

    private BossBarAPI() {

    }

    public static BossBar createBossBar(Player player, String message) {
        return setMessage(player, message, 100);
    }

    public static BossBar createBossBar(Player player, String message, float percentage) {
        return setMessage(player, message, percentage, 0);
    }

    public static BossBar createBossBar(Player player, String message, float percentage, int timeout) {
        return setMessage(player, message, percentage, timeout, 100);
    }

    public static BossBar createBossBar(Player player, String message, float percentage, int timeout, float minHealth) {
        return entries.computeIfAbsent(player.getUniqueId(), k -> new SimpleBossBar(player, message, percentage, timeout, minHealth));
    }

    public static BossBar setMessage(Player player, String message) {
        return setMessage(player, message, 100);
    }

    public static BossBar setMessage(Player player, String message, float percentage) {
        return setMessage(player, message, percentage, 0);
    }

    public static BossBar setMessage(Player player, String message, float percentage, int timeout) {
        return setMessage(player, message, percentage, timeout, 100);
    }

    public static BossBar setMessage(Player player, String message, float percentage, int timeout, float minHealth) {
        BossBar bar = createBossBar(player, message, percentage, timeout, minHealth);

        if (!bar.getMessage().equals(message)) {
            bar.setMessage(message);
        }

        float newHealth = percentage / 100F * bar.getMaxHealth();
        if (bar.getHealth() != newHealth) {
            bar.setHealth(percentage);
        }

        if (!bar.isVisible()) {
            bar.setVisible(true);
        }

        return bar;
    }

    public static boolean hasBossBar(@NotNull Player player) {
        return entries.containsKey(player.getUniqueId());
    }

    public static BossBar getBossBar(Player player) {
        return entries.get(player.getUniqueId());
    }

    public static String getMessage(Player player) {
        BossBar bar = getBossBar(player);
        if (bar == null) return null;

        return bar.getMessage();
    }

    public static void setHealth(Player player, float percentage) {
        BossBar bar = getBossBar(player);
        if (bar == null) return;

        bar.setHealth(percentage);
    }

    public static float getHealth(@NotNull Player player) {
        BossBar bar = getBossBar(player);
        if (bar == null) return -1;

        return bar.getHealth();
    }


    public static void removeBossBar(@NotNull Player player) {
        BossBar bar = getBossBar(player);
        if (bar != null) bar.setVisible(false);

        entries.remove(player.getUniqueId());
    }

    public static void removeBossBars() {
        for (BossBar bossBar : entries.values()) {
            bossBar.setVisible(false);
        }

        entries.clear();
    }

    public static Collection<BossBar> getBossBars() {
        return Collections.unmodifiableCollection(entries.values());
    }
}