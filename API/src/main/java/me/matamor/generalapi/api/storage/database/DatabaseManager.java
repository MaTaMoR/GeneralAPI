package me.matamor.generalapi.api.storage.database;

import me.matamor.minesoundapi.storage.DataStorage;
import me.matamor.minesoundapi.storage.database.queries.Queries;
import me.matamor.minesoundapi.storage.database.queries.builder.QueryBuilder;
import me.matamor.minesoundapi.storage.database.queries.builder.SimpleQueryBuilder;
import me.matamor.minesoundapi.utils.Name;
import me.matamor.minesoundapi.utils.Validate;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseManager extends DataStorage, Name, Closeable {

    ConnectionHandler getConnectionHandler();

    Queries getQueries();

    default QueryBuilder queryBuilder() {
        return new SimpleQueryBuilder(this);
    }

    default void onConnect(Connection connection) throws SQLException {

    }

    default boolean loadDatabase() {
        try {
            //Create connection
            try (Connection connection = getConnectionHandler().openConnection().getConnection()) {
                getRunnableQueue().getControl().set(true);
                getRunnableQueue().start();

                //Execute create table query
                onConnect(connection);
                return true;
            }
        } catch (DatabaseException | SQLException e) {
            throw new DatabaseException("[Database: " + getName() + "] Couldn't create connection");
        }
    }

    default boolean isClosed() {
        return getConnectionHandler().getDataSource() == null || getConnectionHandler().getDataSource().isClosed();
    }

    default void close() {
        if (!isClosed()) {
            getConnectionHandler().getDataSource().close();
        }

        getRunnableQueue().getControl().set(false);
    }

    default Connection getConnection() throws SQLException {
        Validate.isFalse(isClosed(), "Connection is closed!");

        return getConnectionHandler().getDataSource().getConnection();
    }
}
