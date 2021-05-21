package me.matamor.generalapi.api.reflections;

import lombok.Getter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassTemplate<T> {

    @Getter
    private Class<T> type;

    private List<SafeField<?>> fields;

    protected void setClass(Class<T> type) {
        this.type = type;
        this.fields = null;
    }

    public boolean isValid() {
        return this.type != null;
    }

    public List<SafeField<?>> getFields() {
        if (this.fields == null) {
            if (this.type == null) {
                this.fields = Collections.emptyList();
            } else {
                this.fields = Collections.unmodifiableList(fillFields(new ArrayList<>(), this.type));
            }
        }

        return this.fields;
    }

    public T newInstance() {
        if (this.type == null) throw new IllegalStateException("Class is null");

        try {
            return this.type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isInstance(Object object) {
        return this.type.isInstance(object);
    }

    public boolean isAssignableFrom(Class<?> clazz) {
        return this.type.isAssignableFrom(clazz);
    }

    public boolean isType(Object object) {
        return object != null && isType(object.getClass());
    }

    public boolean isType(Class<?> clazz) {
        return clazz != null && this.type.equals(clazz);
    }

    public SafeConstructor<T> getConstructor(Class<?>... parameterTypes) {
        return new SafeConstructor<>(getType(), parameterTypes);
    }

    public <K> SafeField<K> getField(String name) {
        return new SafeField<>(getType(), name);
    }

    public <K> SafeMethod<K> getMethod(String name, Class<?>... parameterTypes) {
        return new SafeMethod<>(getType(), name, parameterTypes);
    }

    public static List<SafeField<?>> fillFields(List<SafeField<?>> fields, Class<?> clazz) {
        if (clazz == null) return null;

        Field[] declared = clazz.getDeclaredFields();
        List<SafeField<?>> newFields = new ArrayList<>(declared.length);

        for (Field field : declared) {
            if (Modifier.isStatic(field.getModifiers())) {
                newFields.add(new SafeField<>(field));
            }
        }

        fields.addAll(0, newFields);

        return fillFields(fields, clazz.getSuperclass());
    }

    public static <T> ClassTemplate<T> getTemplate(Class<T> clazz) {
        ClassTemplate<T> template = new ClassTemplate<>();
        template.setClass(clazz);

        return template;
    }
}
