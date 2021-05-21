package me.matamor.generalapi.api.utils;

import me.matamor.minesoundapi.reflections.SafeConstructor;
import me.matamor.minesoundapi.reflections.SafeField;
import me.matamor.minesoundapi.reflections.SafeMethod;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import sun.reflect.ConstructorAccessor;
import us.swiftex.custominventories.utils.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Minecraft {

    public static final Version VERSION;
    static final Pattern NUMERIC_VERSION_PATTERN = Pattern.compile("v([0-9])_([0-9])_R([0-9])");

    private static Class<?> craftEntity;

    static {
        VERSION = Version.getVersion();
        craftEntity = Reflections.getClass("{obc}.entity.CraftEntity");
    }

    public static String getVersion() {
        return VERSION.name() + ".";
    }

    public static Object getHandle(Object object) throws ReflectiveOperationException {
        Method method;

        try {
            method = AccessUtil.setAccessible(object.getClass().getDeclaredMethod("getHandle"));
        } catch (ReflectiveOperationException e) {
            method = AccessUtil.setAccessible(craftEntity.getDeclaredMethod("getHandle"));
        }

        return method.invoke(object);
    }

    public static Entity getBukkitEntity(Object object) throws ReflectiveOperationException {
        Method method;

        try {
            method = AccessUtil.setAccessible(object.getClass().getDeclaredMethod("getBukkitEntity"));
        } catch (ReflectiveOperationException e) {
            method = AccessUtil.setAccessible(craftEntity.getDeclaredMethod("getHandle"));
        }

        return (Entity) method.invoke(object);
    }


    public static Object getHandleSilent(Object object) {
        try {
            return getHandle(object);
        } catch (Exception e) {
        }

        return null;
    }

    public static Object newEnumInstance(Class clazz, Class[] types, Object[] values) throws ReflectiveOperationException {
        SafeConstructor constructor = new SafeConstructor<>(clazz, types);
        SafeField accessorField = new SafeField(Constructor.class, "constructorAccessor");
        ConstructorAccessor constructorAccessor = (ConstructorAccessor) accessorField.get(constructor);

        if (constructorAccessor == null) {
            new SafeMethod<>(Constructor.class, "acquireConstructorAccessor").invoke(constructor);
            constructorAccessor = (ConstructorAccessor) accessorField.get(constructor);
        }

        return constructorAccessor.newInstance(values);

    }

    public enum Version {

        UNKNOWN(-1) {
            @Override
            public boolean matchesPackageName(String packageName) {
                return false;
            }
        },

        v1_7_R1(10701),
        v1_7_R2(10702),
        v1_7_R3(10703),
        v1_7_R4(10704),

        v1_8_R1(10801),
        v1_8_R2(10802),
        v1_8_R3(10803),
        //Does this even exists?
        v1_8_R4(10804),

        v1_9_R1(10901),
        v1_9_R2(10902);

        private int version;

        Version(int version) {
            this.version = version;
        }

        public static Version getVersion() {
            String name = Bukkit.getServer().getClass().getPackage().getName();
            String versionPackage = name.substring(name.lastIndexOf('.') + 1) + ".";

            for (Version version : values()) {
                if (version.matchesPackageName(versionPackage)) {
                    return version;
                }
            }

            Matcher matcher = NUMERIC_VERSION_PATTERN.matcher(versionPackage);
            while (matcher.find()) {
                if (matcher.groupCount() < 3) continue;

                String majorString = matcher.group(1);
                String minorString = matcher.group(2);
                if (minorString.length() == 1) {
                    minorString = "0" + minorString;
                }
                String patchString = matcher.group(3);
                if (patchString.length() == 1) {
                    patchString = "0" + patchString;
                }

                String numVersionString = majorString + minorString + patchString;
                int numVersion = Integer.parseInt(numVersionString);
                String packge = versionPackage.substring(0, versionPackage.length() - 1);

                try {
                    // Add enum value
                    SafeField valuesField = new SafeField<>(Version.class, "$VALUES");
                    Version[] oldValues = (Version[]) valuesField.get(null);
                    Version[] newValues = new Version[oldValues.length + 1];
                    System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
                    Version dynamicVersion = (Version) newEnumInstance(Version.class, new Class[]{
                            String.class,
                            int.class,
                            int.class}, new Object[]{
                            packge,
                            newValues.length - 1,
                            numVersion});
                    newValues[newValues.length - 1] = dynamicVersion;
                    valuesField.set(null, newValues);

                    return dynamicVersion;
                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                }
            }

            return UNKNOWN;
        }

        public int version() {
            return version;
        }

        public boolean olderThan(Version version) {
            return version() < version.version();
        }

        public boolean newerThan(Version version) {
            return version() >= version.version();
        }

        public boolean inRange(Version oldVersion, Version newVersion) {
            return newerThan(oldVersion) && olderThan(newVersion);
        }

        public boolean matchesPackageName(String packageName) {
            return packageName.toLowerCase().contains(name().toLowerCase());
        }

        @Override
        public String toString() {
            return name() + " (" + version() + ")";
        }
    }
}
