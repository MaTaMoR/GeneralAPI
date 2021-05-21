package me.matamor.generalapi.api.utils;

import me.matamor.minesoundapi.config.IConfig;

public interface BasicLoadable {

    IConfig getConfig();

    void load();

}
