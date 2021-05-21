package me.matamor.generalapi.api.reflections;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Packet {

    @Getter
    public Object packet;

    public <T> void write(FieldAccessor<T> fieldAccessor, T value) {
        fieldAccessor.set(this.packet, value);
    }

    public void write(String fieldName, Object value) {
        FieldUtils.set(this.packet, fieldName, value);
    }

    public <T> T read(FieldAccessor<T> fieldAccessor) {
        return fieldAccessor.get(this.packet);
    }

    public Object read(String fieldName) {
        return FieldUtils.get(this.packet, fieldName);
    }
}
