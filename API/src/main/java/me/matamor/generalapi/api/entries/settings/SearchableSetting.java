package me.matamor.generalapi.api.entries.settings;

import me.matamor.minesoundapi.settings.SimpleSetting;
import me.matamor.minesoundapi.utils.Name;

public abstract class SearchableSetting<T> extends SimpleSetting<T> implements Name {

    public SearchableSetting(String path, T defaultSetting) {
        super(path, defaultSetting);
    }
}
