package me.matamor.generalapi.api.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.minesoundapi.entries.DataEntry;
import me.matamor.minesoundapi.identifier.Identifier;
import me.matamor.minesoundapi.storage.entry.EntryDataStorage;

@AllArgsConstructor
public abstract class SimpleDataProvider<T extends DataEntry> implements DataProvider<T> {

    @Getter
    private final Class<T> dataClass;

    @Getter
    private final EntryDataStorage<Identifier, T> storage;

}
