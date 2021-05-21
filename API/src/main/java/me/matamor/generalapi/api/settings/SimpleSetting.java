package me.matamor.generalapi.api.settings;

public abstract class SimpleSetting<T> implements Setting<T> {

    private final String path;
    private final T defaultSetting;
    protected T loaded;

    public SimpleSetting(String path, T defaultSetting) {
        this.path = path;
        this.defaultSetting = defaultSetting;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public T getDefault() {
        return defaultSetting;
    }

    @Override
    public T get() {
        return loaded;
    }
}
