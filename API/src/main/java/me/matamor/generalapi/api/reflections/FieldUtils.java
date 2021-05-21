package me.matamor.generalapi.api.reflections;

import java.lang.reflect.Field;

public class FieldUtils {

    private FieldUtils() {

    }

    public static void set(Object instance, String fieldName, Object value) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
            field.setAccessible(!field.isAccessible());

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Object get(Object instance, String fieldName) {
        Object object = null;

        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            object = field.get(instance);
            field.setAccessible(!field.isAccessible());

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return object;
    }


    public static <T> FieldAccessor<T> getField(Class<?> clazz, String fieldName) {
        try {
            final Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            return new FieldAccessor<T>() {
                @Override
                public boolean isValid() {
                    return field != null;
                }

                @Override
                public T get(Object instance) {
                    try {
                        return (T) field.get(instance);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Cannot access reflection.", e);
                    }
                }

                @Override
                public boolean set(Object instance, T value) {
                    try {
                        field.set(instance, value);
                        return true;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Cannot access reflection.", e);
                    }
                }
            };
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Cannot get field accessor.", e);
        }
    }

    public static <T> SafeField<T> getSafeField(Class<?> clazz, String fieldName) {
        return new SafeField<>(clazz, fieldName);
    }

    public static Field getFieldAt(Class<?> clazz, int index) {
        Field[] fields = clazz.getDeclaredFields();

        for (int i = 0; fields.length > 0; i++) {
            if (i == index) {
                return fields[i];
            }
        }

        return null;
    }
}
