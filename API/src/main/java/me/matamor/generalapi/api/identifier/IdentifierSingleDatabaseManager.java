package me.matamor.generalapi.api.identifier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class IdentifierSingleDatabaseManager extends Singlle<UUID, Identifier> {

    public IdentifierSingleDatabaseManager(Plugin plugin) {
        this(plugin, false);
    }

    public IdentifierSingleDatabaseManager(Plugin plugin, boolean open) {
        super(plugin, "Identifier", 1.0, open);
    }

    @Override
    public void executeInsertQuery(PreparedStatement statement, Identifier data) throws SQLException {
        statement.setInt(1, data.getId());
        statement.setString(2, data.getUUID().toString());
        statement.setString(3, data.getName());

        statement.executeUpdate();
    }

    @Override
    public void createSelectQuery(PreparedStatement statement, UUID key) throws SQLException {
        statement.setString(1, key.toString());
    }

    @Override
    public Identifier deserialize(ResultSet resultSet, UUID uuid) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");

        return new Identifier(this, id, uuid, name);
    }

    @Override
    public void executeRemoveQuery(PreparedStatement statement, UUID key) throws SQLException {
        statement.setString(1, key.toString());

        statement.executeUpdate();
    }

    public Identifier loadOrCreate(UUID uuid, String name) throws SQLException {
        Identifier identifier = load(uuid);

        if (identifier == null) {
            try (Connection connection = getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(getQueries().getInsert())) {
                    statement.setString(2, uuid.toString());
                    statement.setString(3, name);

                    statement.executeUpdate();

                    identifier = new Identifier(this, statement.getGeneratedKeys().getInt(1), uuid, name);
                }
            }
        }

        return identifier;
    }
}
