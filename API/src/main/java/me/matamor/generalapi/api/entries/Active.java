package me.matamor.generalapi.api.entries;

public interface Active<T> {

    void setActive(T active);

    boolean hasActive();

    T getActive();

}
