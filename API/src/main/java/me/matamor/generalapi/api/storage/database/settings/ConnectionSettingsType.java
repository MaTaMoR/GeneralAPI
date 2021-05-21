package me.matamor.generalapi.api.storage.database.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.generalapi.api.utils.PrimitiveUtils;

@AllArgsConstructor
public enum ConnectionSettingsType {

    TYPE(String.class),
    IP(String.class),
    PORT(Integer.class),
    DATABASE(String.class),
    USERNAME(String.class),
    PASSWORD(String.class);

    @Getter
    private final Class<?> targetClass;

    @Getter
    private final String key = name().replace("_", "");

    public boolean isValid(Object object) {
        return PrimitiveUtils.isInstance(this.targetClass, object);
    }
}
