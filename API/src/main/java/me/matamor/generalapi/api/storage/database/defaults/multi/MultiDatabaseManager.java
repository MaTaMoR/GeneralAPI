package me.matamor.generalapi.api.storage.database.defaults.multi;

import me.matamor.minesoundapi.storage.database.EntryDatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public interface MultiDatabaseManager<K, V> extends EntryDatabaseManager<K, V> {

    Collection<String> getCreateQueries();

    void saveMulti(V value, Connection connection) throws SQLException;

    V loadMulti(K key, Connection connection) throws SQLException;

    void deleteMulti(K key, Connection connection) throws SQLException;

}
