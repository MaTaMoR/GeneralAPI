package me.matamor.generalapi.api.utils;

import me.matamor.minesoundapi.reflections.SafeConstructor;
import me.matamor.minesoundapi.reflections.SafeField;
import me.matamor.minesoundapi.reflections.SafeMethod;
import us.swiftex.custominventories.utils.Reflections;

import javax.annotation.Nullable;

public class DataWatcher {

    private static final Class<?> ITEM_STACK_CLASS = Reflections.getClass("{nms}.ItemStack");
    private static final Class<?> BLOCK_POSITION_CLASS = Reflections.getClass("{nms}.BlockPosition");
    private static final Class<?> VECTOR3F_CLASS = Reflections.getClass("{nms}.Vector3f");
    private static final Class<?> DATA_WATCHER_CLASS = Reflections.getClass("{nms}.DataWatcher");
    private static final Class<?> ENTITY_CLASS = Reflections.getClass("{nms}.Entity");
    private static final Class<?> MAP_CLASS = Reflections.getClass("gnu.trove.map.TIntObjectMap");

    private static final SafeField<Object> WATCHABLE_DATA_VALUES = new SafeField<>(DATA_WATCHER_CLASS, "dataValues");
    private static final SafeMethod<Object> MAP_PUT = new SafeMethod<>(MAP_CLASS, "put", int.class, Object.class);
    private static final SafeMethod<Object> MAP_GET = new SafeMethod<>(MAP_CLASS, "get", int.class);

    public static Object newDataWatcher(@Nullable Object entity) throws ReflectiveOperationException {
        return new SafeConstructor<>(DATA_WATCHER_CLASS, ENTITY_CLASS).newInstance(entity);
    }

    public static Object setValue(Object dataWatcher, int index, Object value) throws ReflectiveOperationException {
        if (Minecraft.VERSION.olderThan(Minecraft.Version.v1_9_R1)) {
            return V1_8.setValue(dataWatcher, index, value);
        } else {
            throw new UnsupportedOperationException("1.9 not supported");
        }
    }

    public static Object getValue(DataWatcher dataWatcher, int index) throws ReflectiveOperationException {
        if (Minecraft.VERSION.olderThan(Minecraft.Version.v1_9_R1)) {
            return V1_8.getValue(dataWatcher, index);
        } else {
            throw new UnsupportedOperationException("1.9 not supported");
        }
    }

    //TODO: update type-ids to 1.9
    public static int getValueType(Object value) {
        int type = 0;

        if (value instanceof Number) {
            if (value instanceof Byte) {
                type = 0;
            } else if (value instanceof Short) {
                type = 1;
            } else if (value instanceof Integer) {
                type = 2;
            } else if (value instanceof Float) {
                type = 3;
            }
        } else if (value instanceof String) {
            type = 4;
        } else if (value != null && value.getClass().equals(ITEM_STACK_CLASS)) {
            type = 5;
        } else if (value != null && (value.getClass().equals(BLOCK_POSITION_CLASS))) {
            type = 6;
        } else if (value != null && value.getClass().equals(VECTOR3F_CLASS)) {
            type = 7;
        }

        return type;
    }

    public static class V1_8 {

        private static final Class<?> WATCHABLE_OBJECT_CLASS = Reflections.getClass("{nms}.DataWatcher$WatchableObject"); //<=1.8 only
        private static final SafeConstructor<?> WATCHABLE_OBJECT_CONSTRUCTOR = new SafeConstructor<>(WATCHABLE_OBJECT_CLASS, int.class, int.class, Object.class);

        public static Object newWatchableObject(int index, Object value) throws ReflectiveOperationException {
            return newWatchableObject(getValueType(value), index, value);
        }

        public static Object newWatchableObject(int type, int index, Object value) throws ReflectiveOperationException {
            return WATCHABLE_OBJECT_CONSTRUCTOR.newInstance(type, index, value);
        }

        public static Object setValue(Object dataWatcher, int index, Object value) throws ReflectiveOperationException {
            int type = getValueType(value);

            Object map = WATCHABLE_DATA_VALUES.get(dataWatcher);
            MAP_PUT.invoke(map, index, newWatchableObject(type, index, value));

            return dataWatcher;
        }

        public static Object getValue(Object dataWatcher, int index) throws ReflectiveOperationException {
            Object map = WATCHABLE_DATA_VALUES.get(dataWatcher);
            return MAP_GET.invoke(map, index);
        }
    }

    private DataWatcher() {

    }
}