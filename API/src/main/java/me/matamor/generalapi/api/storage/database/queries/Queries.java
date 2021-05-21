package me.matamor.generalapi.api.storage.database.queries;

import me.matamor.minesoundapi.storage.database.DatabaseManager;
import me.matamor.minesoundapi.utils.ConfigLoadable;

public interface Queries extends ConfigLoadable {

    DatabaseManager getDatabaseManager();

    void checkFile();

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
