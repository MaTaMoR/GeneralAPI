package me.matamor.generalapi.api.utils.variables;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SimpleVariableHandler<T extends Variable<V>, V> implements VariableHandler<T, V> {

    private final Map<String, T> entries = new HashMap<>();

    @Override
    public T register(T object) {
        if(entries.containsKey(object.getName())) throw new RuntimeException("The value " + object.getName() + " is already registered");

        entries.put(object.getName(), object);
        return object;
    }

    @Override
    public T get(String key) {
        return this.entries.get(key);
    }

    @Override
    public void unregister(String name) {
        this.entries.remove(name);
    }

    @Override
    public Collection<T> getVariables() {
        return this.entries.values();
    }

    @Override
    public String replace(@NotNull V object, @NotNull String text) {
        for(T variable : getVariables()) {
            if(text.contains(variable.getName())) {
                String replacement = variable.getReplacement(object);
                if (replacement == null) continue;

                text = text.replace(variable.getName(), replacement);
            }
        }

        return text;
    }
}
