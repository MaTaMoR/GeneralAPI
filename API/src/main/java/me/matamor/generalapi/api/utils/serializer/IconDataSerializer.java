package me.matamor.generalapi.api.utils.serializer;

import me.matamor.minesoundapi.utils.IconData;
import me.matamor.minesoundapi.utils.Utils;
import org.bukkit.configuration.MemorySection;
import us.swiftex.custominventories.utils.CastUtils;
import us.swiftex.custominventories.utils.CustomItem;

import java.util.LinkedHashMap;
import java.util.Map;

public class IconDataSerializer implements Serializer<IconData> {

    @Override
    public Map<String, Object> serialize(IconData object) throws SerializationException {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("Name", object.getName());
        map.put("Icon", CustomItem.serialize(object.getIcon()));

        return map;
    }

    @Override
    public IconData deserialize(Object serialized) throws SerializationException {
        Map<String, Object> map = asMap(serialized);

        String name = CastUtils.asString(map.get("Name"));
        CustomItem icon = CustomItem.deserialize(asMap(map.get("Icon")));

        return new IconData(name, icon);
    }
}