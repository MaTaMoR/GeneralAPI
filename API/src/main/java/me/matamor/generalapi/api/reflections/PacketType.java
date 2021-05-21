package me.matamor.generalapi.api.reflections;

import me.matamor.minesoundapi.reflections.PacketTypeClasses.*;
import us.swiftex.custominventories.utils.Reflections;

public class PacketType extends ClassTemplate<Object> {

    public static final ClassTemplate<?> DEFAULT = getTemplate(Reflections.getMinecraftClass("Packet"));

    //Chat
    public static final PacketPlayOutTitle OUT_TITLE = new PacketPlayOutTitle();
    public static final PacketPlayOutChat OUT_CHAT = new PacketPlayOutChat();
    public static final PacketPlayOutPlayerListHeaderFooter TAB_LIST = new PacketPlayOutPlayerListHeaderFooter();

    //Scoreboard
    public static final PacketPlayOutScoreboardDisplayObjective OUT_SCOREBOARD_DISPLAY_OBJECTIVE = new PacketPlayOutScoreboardDisplayObjective();
    public static final PacketPlayOutScoreboardObjective OUT_SCOREBOARD_OBJECTIVE = new PacketPlayOutScoreboardObjective();
    public static final PacketPlayOutScoreboardScore OUT_SCOREBOARD_SCORE = new PacketPlayOutScoreboardScore();
    public static final PacketPlayOutScoreboardTeam OUT_SCOREBOARD_TEAM = new PacketPlayOutScoreboardTeam();
    public static final PacketPlayOutBlockAction OUT_BLOCK_ACTION = new PacketPlayOutBlockAction();

    //Entity
    public static final PacketPlayOutEntityStatus OUT_ENTITY_STATUS = new PacketPlayOutEntityStatus();
    public static final PacketPlayOutSpawnEntityLiving OUT_SPAWN_ENTITY_LIVING = new PacketPlayOutSpawnEntityLiving();
    public static final PacketPlayOutEntityTeleport OUT_ENTITY_TELEPORT = new PacketPlayOutEntityTeleport();
    public static final PacketPlayOutEntityMetadata OUT_ENTITY_METADATA = new PacketPlayOutEntityMetadata();
    public static final PacketPlayOutEntityDestroy OUT_ENTITY_DESTROY = new PacketPlayOutEntityDestroy();

    protected PacketType() {
        this(null);
    }

    private PacketType(Class<?> packetClass) {
        if (packetClass == null) packetClass = Reflections.getMinecraftClass(getClass().getSimpleName());

        this.setClass((Class<Object>) packetClass);
    }
}
