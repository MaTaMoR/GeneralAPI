package me.matamor.generalapi.api.storage;

import me.matamor.minesoundapi.data.DataManager;
import me.matamor.minesoundapi.entries.DataEntries;
import me.matamor.minesoundapi.entries.DataStorageManager;
import me.matamor.minesoundapi.entries.settings.SettingsManager;
import me.matamor.minesoundapi.identifier.IdentifierManager;
import org.bukkit.plugin.Plugin;

public interface DataHandler {

    Plugin getPlugin();

    DataEntries getDataEntries();

    InstanceProviderManager getInstanceProviderManager();

    DataStorageManager getStorageManager();

    SettingsManager getSettingsManager();

    IdentifierManager getIdentifierManager();

    DataManager getDataManager();

}

