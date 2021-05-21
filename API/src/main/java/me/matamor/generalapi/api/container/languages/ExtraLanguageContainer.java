package me.matamor.generalapi.api.container.languages;

import lombok.Getter;
import me.matamor.minesoundapi.config.IConfig;
import me.matamor.minesoundapi.container.ContainerEntry;
import me.matamor.minesoundapi.container.SimpleContainer;
import me.matamor.minesoundapi.utils.FileUtils;
import me.matamor.minesoundapi.utils.serializer.Serializer;

import java.util.Arrays;
import java.util.Collections;

public class ExtraLanguageContainer<T> extends SimpleContainer<T> {

    @Getter
    private final Language language;

    @Getter
    private final IConfig config;

    public ExtraLanguageContainer(Class<? extends ContainerEntry<T>> enumClass, Serializer<T> serializer, Language language) {
        super(FileUtils.getPlugin(enumClass), serializer, (enumClass.isEnum() ? Arrays.asList(enumClass.getEnumConstants()) : Collections.emptyList()));

        this.language = language;
        this.config = new IConfig(getPlugin(), FileUtils.getFilePath(enumClass).replace("{language}", language.getName()));

        load();
    }
}
