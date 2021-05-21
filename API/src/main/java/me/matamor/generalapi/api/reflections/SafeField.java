package me.matamor.generalapi.api.reflections;

import java.lang.reflect.Field;

public class SafeField<T> implements FieldAccessor<T> {

    private Field field;

    public SafeField(Field field) {
        if(!field.isAccessible()) {
            try {
                field.setAccessible(true);
            } catch (SecurityException e) {
                e.printStackTrace();
                field = null;
            }
        }

        this.field = field;
    }

    public SafeField(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            this.field = field;

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return field.getName();
    }

    @Override
    public boolean isValid() {
        return field != null;
    }

    @Override
    public T get(Object instance) {
        if(field == null) {
            return null;
        }

        try {
            return (T) field.get(instance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            this.field = null;
        }

        return null;
    }

    @Override
    public boolean set(Object instance, T value) {
        if(field == null) {
            return false;
        }

        try {
            field.set(instance, value);
            return true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            this.field = null;
        }

        return false;
    }

}
