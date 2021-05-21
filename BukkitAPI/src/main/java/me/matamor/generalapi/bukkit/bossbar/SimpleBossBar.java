package me.matamor.generalapi.bukkit.bossbar;

import me.matamor.minesoundapi.MineSoundAPI;
import me.matamor.minesoundapi.reflections.PacketType;
import me.matamor.minesoundapi.reflections.PacketUtil;
import me.matamor.minesoundapi.utils.DataWatcher;
import me.matamor.minesoundapi.utils.Utils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;

public class SimpleBossBar extends BukkitRunnable implements BossBar {

    protected static int ENTITY_DISTANCE = 32;

    protected final int id;

    protected final Player receiver;
    protected String message;
    protected float health;
    protected float healthMinus;
    protected float minHealth = 1;

    protected Location location;
    protected World world;
    protected boolean visible = false;
    protected Object dataWatcher;

    protected SimpleBossBar(Player player, String message, float percentage, int timeout, float minHealth) {
        this.id = ThreadLocalRandom.current().nextInt();

        this.receiver = player;
        this.message = message;
        this.health = percentage / 100F * this.getMaxHealth();
        this.minHealth = minHealth;
        this.world = player.getWorld();
        this.location = this.makeLocation(player.getLocation());

        if (percentage < minHealth) {
            BossBarAPI.removeBossBar(player);
        }

        if (timeout > 0) {
            this.healthMinus = this.getMaxHealth() / timeout;
            this.runTaskTimer(MineSoundAPI.getInstance(), 20, 20);
        }
    }

    protected Location makeLocation(Location base) {
        return base.getDirection().multiply(ENTITY_DISTANCE).add(base.toVector()).toLocation(this.world);
    }

    @Override
    public Player getReceiver() {
        return receiver;
    }

    @Override
    public float getMaxHealth() {
        return 300;
    }

    @Override
    public void setHealth(float percentage) {
        this.health = percentage / 100F * this.getMaxHealth();
        if (this.health <= this.minHealth) {
            BossBarAPI.removeBossBar(this.receiver);
        } else {
            this.sendMetadata();
        }
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
        if (isVisible()) {
            sendMetadata();
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public void run() {
        this.health -= this.healthMinus;
        if (this.health <= this.minHealth) {
            BossBarAPI.removeBossBar(this.receiver);

            System.out.println("Removeee");
        } else {
            sendMetadata();
        }
    }

    @Override
    public void setVisible(boolean flag) {
        if (this.visible == flag) return;

        if (flag) {
            spawn();
        } else {
            destroy();
        }
    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }

    @Override
    public void updateMovement() {
        if (!this.visible) return;

        this.location = this.makeLocation(this.receiver.getLocation());
        PacketUtil.sendPacket(this.receiver, PacketType.OUT_ENTITY_TELEPORT.newInstance(this.id, this.location));
    }

    protected void updateDataWatcher() {
        if (this.dataWatcher == null) {
            try {
                this.dataWatcher = DataWatcher.newDataWatcher(null);

                DataWatcher.setValue(this.dataWatcher, 17, 0);
                DataWatcher.setValue(this.dataWatcher, 18, 0);
                DataWatcher.setValue(this.dataWatcher, 19, 0);

                DataWatcher.setValue(this.dataWatcher, 20, 1000);// Invulnerable time (1000 = very small)
                DataWatcher.setValue(this.dataWatcher, 0, (byte) (1 << 5));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        try {
            DataWatcher.setValue(this.dataWatcher, 6, this.health);

            DataWatcher.setValue(this.dataWatcher, 10, Utils.color(this.message));
            DataWatcher.setValue(this.dataWatcher, 2, Utils.color(this.message));

            DataWatcher.setValue(this.dataWatcher, 11, (byte) 1);
            DataWatcher.setValue(this.dataWatcher, 3, (byte) 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void sendMetadata() {
        this.updateDataWatcher();

        try {
            DataWatcher.setValue(dataWatcher, 2, (this.message == null ? "" : Utils.color(this.message)));
            DataWatcher.setValue(dataWatcher, 3, (byte) (this.message == null || this.message.isEmpty() ? 0 : 1));

            PacketUtil.sendPacket(this.receiver, PacketType.OUT_ENTITY_METADATA.newInstance(this.id, this.dataWatcher, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void spawn() {
        this.updateMovement();
        this.updateDataWatcher();

        PacketUtil.sendPacket(this.receiver, PacketType.OUT_SPAWN_ENTITY_LIVING.newInstance(this.id, 64, this.location, this.dataWatcher));

        this.visible = true;
        this.sendMetadata();
        this.updateMovement();
    }

    protected void destroy() {
        try {
            this.cancel();
        } catch (IllegalStateException ignored) {
        }

        PacketUtil.sendPacket(this.receiver, PacketType.OUT_ENTITY_DESTROY.newInstance(this.id));
        this.visible = false;
    }
}