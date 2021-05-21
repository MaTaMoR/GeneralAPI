package me.matamor.generalapi.api.entries.defaults;

import lombok.Getter;
import me.matamor.minesoundapi.identifier.Identifier;
import me.matamor.minesoundapi.storage.DataStorage;
import me.matamor.minesoundapi.entries.Active;
import me.matamor.minesoundapi.utils.Name;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class ActiveDataEntry<T extends Name> extends ListDataEntry<T> implements Active<T> {

    @Getter
    private T active;

    public ActiveDataEntry(Plugin plugin, Identifier identifier, DataStorage dataStorage) {
        super(plugin, identifier, dataStorage);
    }

    @Override
    public void setActive(T active) {
        this.active = active;

        if (hasActive()) {
            add(active);
        }
    }

    @Override
    public boolean hasActive() {
        return this.active != null;
    }
}
