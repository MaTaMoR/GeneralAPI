package me.matamor.generalapi.api.reflections;

import me.matamor.minesoundapi.reflections.conversion.type.Conversion;

public class NMSPacket extends PacketType {

    protected final SafeConstructor<Packet> constructor = getPacketConstructor();

    public SafeConstructor<Packet> getPacketConstructor(Class<?>... args) {
        return getConstructor(args).translateOutput(Conversion.toPacket);
    }

    @Override
    public Packet newInstance() {
        return this.constructor.newInstance();
    }
}
