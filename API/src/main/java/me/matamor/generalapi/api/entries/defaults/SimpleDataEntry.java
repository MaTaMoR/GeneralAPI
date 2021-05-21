package me.matamor.generalapi.api.entries.defaults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.minesoundapi.identifier.Identifier;
import me.matamor.minesoundapi.storage.DataStorage;
import me.matamor.minesoundapi.utils.Callback;
import me.matamor.minesoundapi.entries.DataEntry;
import org.bukkit.plugin.Plugin;

@AllArgsConstructor
public abstract class SimpleDataEntry implements DataEntry {

    @Getter
    private final Plugin plugin;

    @Getter
    private final Identifier identifier;

    @Getter
    private final DataStorage dataStorage;

    @Override
    public void save() {
        this.dataStorage.save(this);
    }

    @Override
    public void saveAsync(Callback<Boolean> callback) {
        this.dataStorage.saveAsync(this, callback);
    }
}
