package me.matamor.generalapi.api.settings.defaults;

import me.matamor.minesoundapi.config.IConfig;
import me.matamor.minesoundapi.settings.SimpleSetting;

public class BooleanSetting extends SimpleSetting<Boolean> {

    public BooleanSetting(String path, Boolean defaultSetting) {
        super(path, defaultSetting);
    }

    @Override
    public void load(IConfig config) {
        if(config.contains(getPath())) {
            loaded = config.getBoolean(getPath());
        } else {
            config.set(getPath(), getDefault());
            loaded = getDefault();
        }
    }
}
