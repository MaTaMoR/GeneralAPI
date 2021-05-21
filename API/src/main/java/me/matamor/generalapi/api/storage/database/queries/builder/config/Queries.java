package me.matamor.generalapi.api.storage.database.queries.builder.config;

import com.jcdesimp.landlord.storage.database.DatabaseManager;

public interface Queries {

    void load();

    DatabaseManager getDatabaseManager();

    String getCreate();

    String getInsert();

    String getSelect();

    String getDelete();

    String getCreate(String key);

    String getInsert(String key);

    String getSelect(String key);

    String getDelete(String key);

    String getQuery(String path);

}
