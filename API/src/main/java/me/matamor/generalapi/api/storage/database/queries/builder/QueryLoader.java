package me.matamor.generalapi.api.storage.database.queries.builder;

import me.matamor.minesoundapi.storage.database.DatabaseException;

import java.sql.ResultSet;

public interface QueryLoader<T> {

    T deserialize(ResultSet resultSet) throws DatabaseException;

}
