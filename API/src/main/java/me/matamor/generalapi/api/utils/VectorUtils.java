package me.matamor.generalapi.api.utils;

import org.bukkit.util.Vector;

public final class VectorUtils {

    private VectorUtils() {
        
    }
    
    public static Vector rotateVector(Vector vector, double angleX, double angleY, double angleZ) {
        rotateAroundAxisX(vector, angleX);
        rotateAroundAxisY(vector, angleY);
        rotateAroundAxisZ(vector, angleZ);

        return vector;
    }
    
    public static Vector rotateAroundAxisX(Vector vector, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double y = vector.getY() * cos - vector.getZ() * sin;
        double z = vector.getY() * sin + vector.getZ() * cos;

        return vector.setY(y).setZ(z);
    }

    public static Vector rotateAroundAxisY(Vector vector, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double x = vector.getX() * cos + vector.getZ() * sin;
        double z = vector.getX() * -sin + vector.getZ() * cos;

        return vector.setX(x).setZ(z);
    }

    public static Vector rotateAroundAxisZ(Vector vector, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double x = vector.getX() * cos - vector.getY() * sin;
        double y = vector.getX() * sin + vector.getY() * cos;

        return vector.setX(x).setY(y);
    }
}
