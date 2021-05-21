package me.matamor.generalapi.api.settings.defaults;

import me.matamor.minesoundapi.config.IConfig;
import me.matamor.minesoundapi.settings.SimpleSetting;
import me.matamor.minesoundapi.utils.Utils;

public class StringSetting extends SimpleSetting<String> {

    public StringSetting(String path, String defaultSetting) {
        super(path, defaultSetting);
    }

    @Override
    public void load(IConfig config) {
        if(config.contains(getPath())) {
            loaded = config.getString(getPath());
        } else {
            config.set(getPath(), getDefault());
            loaded = getDefault();
        }
    }

    public String getColored() {
        return Utils.color(get());
    }

    public String replace(String key, String value) {
        return get().replace(key, value);
    }
}
