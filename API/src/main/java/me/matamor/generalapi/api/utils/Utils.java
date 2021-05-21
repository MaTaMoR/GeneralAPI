package me.matamor.generalapi.api.utils;

import me.matamor.generalapi.api.config.ISection;

import java.util.Map;

public class Utils {


    public static Map<String, Object> asMap(Object object) {
        if (object instanceof ISection) {
            return ((ISection) object).getValues(false);
        } else if (object instanceof Map) {
            return ((Map<String, Object>) object);
        }

        return null;
    }

}
