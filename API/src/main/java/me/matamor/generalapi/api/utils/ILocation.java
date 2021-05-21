package me.matamor.generalapi.api.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Objects;

public class ILocation implements Cloneable {

    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public ILocation(Location location) {
        this(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public ILocation(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public Block getBlock(World world) {
        return toLocation(world).getBlock();
    }

    public ILocation add(double x, double y, double z) {
        return new ILocation(this.x + x, this.y + y, this.z + z, yaw, pitch);
    }

    public ILocation subtract(double x, double y, double z) {
        return new ILocation(this.x - x, this.y - y, this.z - z, yaw, pitch);
    }

    public Location toLocation(World world) {
        return new Location(world, x, y, z, yaw, pitch);
    }

    public SLocation toSLocation(World world) {
        return new SLocation(world.getName(), x, y, z, yaw, pitch);
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) {
            return true;
        } else if(object instanceof ILocation) {
            ILocation target = (ILocation) object;
            return Objects.equals(x, target.x) && Objects.equals(y, target.y) && Objects.equals(z, target.z) &&
                    Objects.equals(yaw, target.yaw) && Objects.equals(pitch, target.pitch);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, yaw, pitch);
    }

    @Override
    public String toString() {
        return "X : " + (int) x + " Y : " + (int) y + " Z : " + (int) z;
    }

    @Override
    public ILocation clone() {
        try {
            return (ILocation) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
