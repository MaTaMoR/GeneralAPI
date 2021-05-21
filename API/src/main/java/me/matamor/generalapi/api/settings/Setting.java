package me.matamor.generalapi.api.settings;

import me.matamor.minesoundapi.config.IConfig;

public interface Setting<T> {

    String getPath();

    T getDefault();

    void load(IConfig config);

    T get();

}
