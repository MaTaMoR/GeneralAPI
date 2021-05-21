package me.matamor.generalapi.api.reflections;

public interface MethodAccessor<T> {

    boolean isValid();

    T invoke(Object instance, Object... args);
}
