package me.matamor.generalapi.api.utils.manager;

import me.matamor.minesoundapi.utils.Name;
import me.matamor.minesoundapi.utils.Validate;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SimpleManager<T extends Name> implements Manager<T> {

    private final Map<String, T> entries = new HashMap<>();

    @Override
    public T register(T object) {
        Validate.isFalse(this.entries.containsKey(object.getName()), "The value '" + object.getName() + "' is already registered!");

        this.entries.put(object.getName(), object);

        return object;
    }

    @Override
    public T get(String name) {
        return this.entries.get(name);
    }

    @Override
    public void unregister(String name) {
        this.entries.remove(name);
    }

    @Override
    public Collection<T> getValues() {
        return Collections.unmodifiableCollection(this.entries.values());
    }
}
