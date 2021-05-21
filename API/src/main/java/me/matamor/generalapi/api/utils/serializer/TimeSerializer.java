package me.matamor.generalapi.api.utils.serializer;

import me.matamor.custominventories.utils.CastUtils;
import me.matamor.minesoundapi.utils.Time;

public class TimeSerializer implements Serializer<Long> {

    @Override
    public String serialize(Long value) throws SerializationException {
        return Time.toString(value);
    }

    @Override
    public Long deserialize(Object object) throws SerializationException {
        return Time.parseString(CastUtils.asString(object)).toMilliseconds();
    }
}
