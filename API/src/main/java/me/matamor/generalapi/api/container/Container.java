package me.matamor.generalapi.api.container;

import me.matamor.generalapi.api.config.IConfig;

import java.util.Collection;

public interface Container<T> {

    IConfig getConfig();

    Serializer<T> getSerializer();

    Collection<ContainerEntry<T>> getEntries();

    boolean hasEntry(ContainerEntry<T> entry);

    T get(ContainerEntry<T> entry);

    void load();
}
