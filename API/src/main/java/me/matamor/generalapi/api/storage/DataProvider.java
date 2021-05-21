package me.matamor.generalapi.api.storage;

import me.matamor.minesoundapi.entries.DataEntry;
import me.matamor.minesoundapi.identifier.Identifier;
import me.matamor.minesoundapi.storage.entry.EntryDataStorage;

public interface DataProvider<T extends DataEntry> extends InstanceProvider<T> {

    Class<T> getDataClass();

    EntryDataStorage<Identifier, T> getStorage();

}
