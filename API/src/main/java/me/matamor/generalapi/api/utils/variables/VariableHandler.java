package me.matamor.generalapi.api.utils.variables;

import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface VariableHandler<T extends Variable<V>, V> {

    T register(T object);

    T get(String key);

    void unregister(String name);

    Collection<T> getVariables();

    default String replace(@NotNull V object, @NotNull String text) {
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
