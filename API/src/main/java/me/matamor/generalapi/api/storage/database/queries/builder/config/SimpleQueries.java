package me.matamor.generalapi.api.storage.database.queries.builder.config;

import com.jcdesimp.landlord.config.IConfig;
import com.jcdesimp.landlord.storage.database.DatabaseManager;
import com.jcdesimp.landlord.utils.CastUtils;
import com.jcdesimp.landlord.utils.FileUtils;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

public class SimpleQueries implements Queries {

    public static final String FOLDER = "Queries";

    private final Map<String, Map<String, String>> queries = new LinkedHashMap<>();

    @Getter
    private final DatabaseManager databaseManager;

    @Getter
    private final double actualVersion;

    @Getter
    private final IConfig config;

    @Getter
    private String create;

    @Getter
    private String insert;

    @Getter
    private String select;

    @Getter
    private String delete;

    public SimpleQueries(DatabaseManager databaseManager, double actualVersion) {
        this.databaseManager = databaseManager;
        this.actualVersion = actualVersion;
        this.config = new IConfig(databaseManager.getPlugin(), databaseManager.getName(), "Queries");

        //If the Queries aren't updated then delete them, load the new one and copyFile it!
        if (!checkVersion()) {
            resetConfig();
        }
    }

    @Override
    public void load() {
        String connectionType = this.databaseManager.getConnectionHandler().getConnectionType().name();

        if (this.config.contains(connectionType + ".Create")) {
            this.create = this.config.getString(connectionType + ".Create");
        } else {
            this.create = this.config.getString("Create");
        }

        if (this.config.contains(connectionType + ".Insert")) {
            this.insert = this.config.getString(connectionType + ".Insert");
        } else {
            this.insert = this.config.getString("Insert");
        }

        if (this.config.contains(connectionType + ".Select")) {
            this.select = this.config.getString(connectionType + ".Select");
        } else {
            this.select = this.config.getString("Select");
        }

        if (this.config.contains(connectionType + ".Remove")) {
            this.delete = this.config.getString(connectionType + ".Remove");
        } else {
            this.delete = this.config.getString("Remove");
        }

        for (String types : Arrays.asList("Create", "Insert", "Select", "Remove")) {
            if (getConfig().contains(connectionType + "." + types)) {
                if (getConfig().isConfigurationSection(connectionType + "." + types)) {
                    load(types, connectionType + "." + types);
                }
            } else {
                load(types, types);
            }
        }
    }

    @Override
    public String getCreate(String key) {
        return getQuery("Create", key);
    }

    @Override
    public String getInsert(String key) {
        return getQuery("Insert", key);
    }

    @Override
    public String getSelect(String key) {
        return getQuery("Select", key);
    }

    @Override
    public String getDelete(String key) {
        return getQuery("Remove", key);
    }

    @Override
    public String getQuery(String path) {
        return this.config.getString(path);
    }

    private void load(String type, String path) {
        if (getConfig().isConfigurationSection(path)) {
            ConfigurationSection section = getConfig().getConfigurationSection(path);
            Collection<String> keys = section.getKeys(true);

            if (keys.size() > 0) {
                Map<String, String> map = this.queries.computeIfAbsent(type, s -> new LinkedHashMap<>());
                keys.forEach(key -> map.put(key, section.getString(key)));
            }
        }
    }

    private String getQuery(String type, String key) {
        Map<String, String> map = this.queries.get(type);
        return (map == null ? null : map.get(key));
    }

    private boolean checkVersion() {
        boolean updated = false;

        if (this.config.exists()) {
            try {
                double version = CastUtils.asDouble(this.config.get("Version"));

                if (version == this.actualVersion) {
                    updated = true;
                }
            } catch (CastUtils.FormatException ignored) {

            }
        }

        return updated;
    }

    public void resetConfig() {
        //Backup actual config just in case
        if (this.config.exists()) {
            File backupFile = FileUtils.getAvailableFile(new File(this.databaseManager.getPlugin().getDataFolder() + FOLDER + File.separator + "Old" + File.separator),
                    this.databaseManager.getName(), ".yml");

            try {
                FileUtils.copy(this.config.getFile(), backupFile);
            } catch (IOException e) {
                this.databaseManager.getPlugin().getLogger().log(Level.SEVERE, "Couldn't store config!", e);

                if (backupFile.exists() && !backupFile.delete()) {
                    this.databaseManager.getPlugin().getLogger().log(Level.SEVERE, "Could't delete backup file: " + backupFile);
                }
            }
        }

        //Reset config
        reloadConfig();
    }

    private void reloadConfig() {
        this.config.delete();

        try {
            copyFile();

            this.config.reload();
        } catch (IOException e) {
            this.databaseManager.getPlugin().getLogger().log(Level.SEVERE, "Couldn't copy file!", e);
        }
    }

    private void copyFile() throws IOException {
        InputStream inputStream = this.databaseManager.getPlugin().getResource(FOLDER + "/" + this.databaseManager.getName() + ".yml");

        if (inputStream != null) {
            File file = this.config.getFile();

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            Files.copy(inputStream, this.config.getFile().toPath());
        }
    }
}
