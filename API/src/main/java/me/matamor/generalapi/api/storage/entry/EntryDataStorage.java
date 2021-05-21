package me.matamor.generalapi.api.storage.entry;

import me.matamor.minesoundapi.storage.DataStorage;
import me.matamor.minesoundapi.utils.Callback;

import javax.validation.constraints.NotNull;

public interface EntryDataStorage<K, V> extends me.matamor.minesoundapi.storage.DataStorage {

    void save(@NotNull V save);

    default void saveAsync(final @NotNull V value, final Callback<Boolean> callback) {
        runAsync(() -> {
            Exception exception = null;

            try {
                save(value);
            } catch (Exception e) {
                exception = e;
            }

            if (callback != null) {
                callback.done(true, exception);
            }
        });
    }

    V load(@NotNull K key);

    default void loadAsync(@NotNull K key, final @NotNull Callback<V> callback) {
        runAsync(() -> callback.done(load(key), null));
    }

    void delete(@NotNull K key);

    default void deleteAsync(final @NotNull K key, Callback<Boolean> callback) {
        runAsync(() -> {
            Exception exception = null;

            try {
                delete(key);
            } catch (Exception e) {
                exception = e;
            }

            if (callback != null) {
                callback.done(true, exception);
            }
        });
    }
}
