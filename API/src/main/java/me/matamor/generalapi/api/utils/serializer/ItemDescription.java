package me.matamor.generalapi.api.utils.serializer;

import us.swiftex.custominventories.utils.CastUtils;
import us.swiftex.custominventories.utils.CustomItem;
import us.swiftex.custominventories.utils.StringSerializer;

import java.util.HashMap;
import java.util.Map;

public class ItemDescription implements Serializer<CustomItem> {

    @Override
    public Map<String, Object> serialize(CustomItem object) throws SerializationException {
        Map<String, Object> map = new HashMap<>();

        map.put("Item", StringSerializer.toString(object));

        return map;
    }

    @Override
    public CustomItem deserialize(Object serialized) throws SerializationException {
        Map<String, Object> map = asMap(serialized);
        return StringSerializer.getItem(CastUtils.asString(map.get("Item")));
    }
}
