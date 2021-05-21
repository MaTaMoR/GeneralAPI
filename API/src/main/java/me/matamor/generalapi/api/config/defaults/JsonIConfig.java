package me.matamor.generalapi.api.config.defaults;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import me.matamor.generalapi.api.config.IConfig;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class JsonIConfig extends MemoryISection implements IConfig {

    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Getter
    private final File file;

    public JsonIConfig(File file) {
        this.file = file;
    }

    @Override
    public boolean exists() {
        return this.file.exists();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void load() {
        this.content.clear();

        if (this.file.exists()) {
            try {
                Map<String, Object> content = gson.fromJson(new FileReader(this.file), HashMap.class);

                content.forEach(this::set);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void save() {
        Map<String, Object> content = getValues(true);

        this.file.delete();

        String json = gson.toJson(content); // Remember pretty printing? This is needed here.

        try {
            Files.write(this.file.toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
