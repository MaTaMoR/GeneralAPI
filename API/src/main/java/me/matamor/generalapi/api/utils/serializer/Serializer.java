package me.matamor.generalapi.api.utils.serializer;

import me.matamor.generalapi.api.utils.Utils;

import java.util.Map;

public interface Serializer<T> {

    Object serialize(T value) throws SerializationException;

    T deserialize(Object serialized) throws SerializationException;

    default Object get(Map<String, Object> map, String key) throws SerializationException {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            throw new SerializationException(String.format("[%s] Missing map key %s", getClass().getSimpleName(), key));
        }
    }

    default Map<String, Object> asMap(Object object) throws SerializationException {
        Map<String, Object> map = Utils.asMap(object);
        if (map == null) throw new SerializationException("Provided Value isn't a valid Section");

        return map;
    }
}
