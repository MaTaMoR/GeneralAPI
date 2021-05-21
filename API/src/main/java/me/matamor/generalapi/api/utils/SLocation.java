package me.matamor.generalapi.api.utils;

import lombok.Getter;
import lombok.Setter;
import me.matamor.custominventories.utils.CastUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.NumberConversions;

import java.util.LinkedHashMap;
import java.util.Map;

public class SLocation implements ConfigurationSerializable {

    @Getter @Setter
    private World world;

    @Getter @Setter
    private double x;

    @Getter @Setter
    private double y;

    @Getter @Setter
    private double z;

    @Getter @Setter
    private float yaw;

    @Getter @Setter
    private float pitch;

    public SLocation(Location location) {
        this(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public SLocation(World world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public int getBlockX() {
        return locToBlock(this.x);
    }

    public int getBlockY() {
        return locToBlock(this.y);
    }

    public int getBlockZ() {
        return locToBlock(this.z);
    }

    public Block getBlock() {
        return this.world.getBlockAt(getBlockX(), getBlockY(), getBlockZ());
    }

    public SLocation add(double x, double y, double z) {
        return new SLocation(this.world, this.x + x, this.y + y, this.z + z, yaw, pitch);
    }

    public SLocation subtract(double x, double y, double z) {
        return new SLocation(this.world, this.x - x, this.y - y, this.z - z, yaw, pitch);
    }

    public SLocation center() {
        return new SLocation(this.world, getBlockX() + 0.5, this.y, getBlockZ() + 0.5, this.yaw, this.pitch);
    }

    public Location toLocation() {
        return new Location(getWorld(), x, y, z, yaw, pitch);
    }

    public boolean compare(Location other) {
        if (this.world != other.getWorld() && (this.world == null || !this.world.equals(other.getWorld()))) {
            return false;
        } else if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.getX())) {
            return false;
        } else if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.getY())) {
            return false;
        } else if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.getZ())) {
            return false;
        } else if (Float.floatToIntBits(this.pitch) != Float.floatToIntBits(other.getPitch())) {
            return false;
        } else {
            return Float.floatToIntBits(this.yaw) == Float.floatToIntBits(other.getYaw());
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("World", this.world.getName());
        map.put("X", this.x);
        map.put("Y", this.y);
        map.put("Z", this.z);
        map.put("Yaw", this.yaw);
        map.put("Pitch", this.pitch);

        return map;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        } else if (object instanceof SLocation) {
            SLocation other = (SLocation) object;

            if (this.world != other.getWorld() && (this.world == null || !this.world.equals(other.getWorld()))) {
                return false;
            } else if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.getX())) {
                return false;
            } else if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.getY())) {
                return false;
            } else if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.getZ())) {
                return false;
            } else if (Float.floatToIntBits(this.pitch) != Float.floatToIntBits(other.getPitch())) {
                return false;
            } else {
                return Float.floatToIntBits(this.yaw) == Float.floatToIntBits(other.getYaw());
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;

        hash = 19 * hash + (this.world != null ? this.world.hashCode() : 0);
        hash = 19 * hash + (int)(Double.doubleToLongBits(this.x) ^ Double.doubleToLongBits(this.x) >>> 32);
        hash = 19 * hash + (int)(Double.doubleToLongBits(this.y) ^ Double.doubleToLongBits(this.y) >>> 32);
        hash = 19 * hash + (int)(Double.doubleToLongBits(this.z) ^ Double.doubleToLongBits(this.z) >>> 32);
        hash = 19 * hash + Float.floatToIntBits(this.pitch);
        hash = 19 * hash + Float.floatToIntBits(this.yaw);

        return hash;
    }

    @Override
    public String toString() {
        return "W : " + this.world.getName() + " X : " + (int) x + " Y : " + (int) y + " Z : " + (int) z;
    }

    public static SLocation deserialize(Map<String, Object> map) {
        String name = (String) map.get("World");
        double x = CastUtils.asDouble(map.get("X"));
        double y = CastUtils.asDouble(map.get("Y"));
        double z = CastUtils.asDouble(map.get("Z"));
        float yaw = CastUtils.asFloat(map.get("Yaw"));
        float pitch = CastUtils.asFloat(map.get("Pitch"));

        return new SLocation(Bukkit.getWorld(name), x, y, z, yaw, pitch);
    }

    public static int locToBlock(double loc) {
        return NumberConversions.floor(loc);
    }
}
