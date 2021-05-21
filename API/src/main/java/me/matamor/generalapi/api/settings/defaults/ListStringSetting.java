package me.matamor.generalapi.api.settings.defaults;

import me.matamor.minesoundapi.config.IConfig;
import me.matamor.minesoundapi.settings.SimpleSetting;

import java.util.List;

public class ListStringSetting extends SimpleSetting<List<String>> {

    public ListStringSetting(String path, List<String> defaultSetting) {
        super(path, defaultSetting);
    }

    @Override
    public void load(IConfig config) {
        if(config.contains(getPath())) {
            loaded = config.getStringList(getPath());
        } else {
            config.set(getPath(), getDefault());
            loaded = getDefault();
        }
    }
}
