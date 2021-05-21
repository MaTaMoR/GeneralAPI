package me.matamor.generalapi.api.storage.database;

import me.matamor.minesoundapi.storage.entry.EntryDataStorage;

public interface EntryDatabaseManager<K, V> extends DatabaseManager, EntryDataStorage<K, V> {

}
