package me.matamor.generalapi.api.entries;

import me.matamor.minesoundapi.identifier.Identifier;
import me.matamor.minesoundapi.storage.DataStorage;
import me.matamor.minesoundapi.utils.Callback;
import org.bukkit.plugin.Plugin;

public interface DataEntry {

    Plugin getPlugin();

    Identifier getIdentifier();

    DataStorage getDataStorage();

    void save();

    void saveAsync(Callback<Boolean> callback);

}
