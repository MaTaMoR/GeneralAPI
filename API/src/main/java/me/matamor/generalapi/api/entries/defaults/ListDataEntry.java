package me.matamor.generalapi.api.entries.defaults;

import me.matamor.minesoundapi.identifier.Identifier;
import me.matamor.minesoundapi.storage.DataStorage;
import me.matamor.minesoundapi.utils.Name;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class ListDataEntry<T extends Name> extends SimpleDataEntry {

    private final Map<String, T> entries = new HashMap<>();

    public ListDataEntry(Plugin plugin, Identifier identifier, DataStorage dataStorage) {
        super(plugin, identifier, dataStorage);
    }

    public void add(T data) {
        this.entries.put(data.getName(), data);
    }

    public T get(String name) {
        return this.entries.get(name);
    }

    public void remove(T value) {
        remove(value.getName());
    }

    public void remove(String name) {
        this.entries.remove(name);
    }

    public boolean has(T value) {
        return has(value.getName());
    }

    public boolean has(String name) {
        return this.entries.containsKey(name);
    }

    public Collection<T> getValues() {
        return Collections.unmodifiableCollection(this.entries.values());
    }

}
