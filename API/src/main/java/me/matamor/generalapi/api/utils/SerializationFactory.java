package me.matamor.generalapi.api.utils;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

public class SerializationFactory {

    public static void registerClass(Class<? extends ConfigurationSerializable> clazz) {
        ConfigurationSerialization.registerClass(clazz);
    }

    @SafeVarargs
    public static void registerClasses(Class<? extends ConfigurationSerializable>... clazz) {
        for (Class<? extends ConfigurationSerializable> c : clazz) {
            SerializationFactory.registerClass(c);
        }
    }

    public static void registerClasses(Iterable<? extends Class<? extends ConfigurationSerializable>> classes) {
        for (Class<? extends ConfigurationSerializable> clazz : classes) {
            registerClass(clazz);
        }
    }
}