package me.matamor.generalapi.api.storage.database.queries;

import lombok.Getter;
import me.matamor.custominventories.utils.CastUtils;
import me.matamor.minesoundapi.MineSoundAPI;
import me.matamor.minesoundapi.config.IConfig;
import me.matamor.minesoundapi.storage.database.DatabaseException;
import me.matamor.minesoundapi.storage.database.DatabaseManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class SimpleQueries implements Queries {

    public static final String FOLDER = "Queries";

    private final Map<String, String> queries = new LinkedHashMap<>();

    @Getter
    private final DatabaseManager databaseManager;

    @Getter
    private final double actualVersion;

    @Getter
    private final IConfig config;

    public SimpleQueries(DatabaseManager databaseManager, double actualVersion) {
        this.databaseManager = databaseManager;
        this.actualVersion = actualVersion;
        this.config = new IConfig(MineSoundAPI.getInstance(), databaseManager.getName(), "Queries");
    }

    @Override
    public void checkFile() {
        if (!this.config.exists() || !checkVersion()) {
            this.config.delete();

            save();

            this.config.reload();
        }
    }

    @Override
    public void load() {
        checkFile();

        this.queries.clear();

        Set<String> keys = this.config.getKeys(true);
        if (keys.isEmpty()) return;

        String connectionType = this.databaseManager.getConnectionHandler().getConnectionType().name();

        for (String key : keys) {
            if (this.config.isString(key)) {
                String value = this.config.getString(key);

                key = key.replaceFirst(connectionType + ".", "");

                this.queries.put(key, value);
            }
        }
    }

    @Override
    public String getCreate() {
        return findQuery("Create");
    }

    @Override
    public String getInsert() {
        return findQuery("Insert");
    }

    @Override
    public String getSelect() {
        return findQuery("Select");
    }

    @Override
    public String getDelete() {
        return findQuery("Delete");
    }

    @Override
    public String getCreate(String key) {
        return findQuery("Create." + key);
    }

    @Override
    public String getInsert(String key) {
        return findQuery("Insert." + key);
    }

    @Override
    public String getSelect(String key) {
        return findQuery("Select." + key);
    }

    @Override
    public String getDelete(String key) {
        return findQuery("Delete." + key);
    }

    @Override
    public String getQuery(String path) {
        return findQuery(path);
    }

    private String findQuery(String key) {
        String query = this.queries.get(key);
        if (query == null) {
            throw new DatabaseException("Missing query: " + key);
        }

        return query;
    }

    private boolean checkVersion() {
        boolean updated = false;

        try {
            double version = CastUtils.asDouble(this.config.get("Version"));

            if (version == this.actualVersion) {
                updated = true;
            }
        } catch (CastUtils.FormatException ignored) {

        }

        return updated;
    }

    private void save() {
        if (this.config.getFile().exists()) {
            return;
        }

        InputStream inputStream = this.databaseManager.getPlugin().getResource(FOLDER + "/" + this.databaseManager.getName() + ".yml");

        if (inputStream != null) {
            File file = this.config.getFile();

            try {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                Files.copy(inputStream, this.config.getFile().toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
