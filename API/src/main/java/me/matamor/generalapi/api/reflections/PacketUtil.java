package me.matamor.generalapi.api.reflections;

import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import us.swiftex.custominventories.utils.Reflections;
import us.swiftex.custominventories.utils.Validate;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class PacketUtil {

    private PacketUtil() {

    }

    private static final Class<?> CRAFT_PLAYER_CLASS = Reflections.getCraftBukkitClass("entity.CraftPlayer");
    private static final Class<?> CHAT_SERIALIZER = Reflections.getMinecraftClass("IChatBaseComponent$ChatSerializer");
    private static final Class<?> PACKET_CLASS = Reflections.getMinecraftClass("Packet");
    private static final SafeMethod<?> SEND_PACKET_METHOD = new SafeMethod<>(Reflections.getClass("{nms}.PlayerConnection"), "sendPacket", PACKET_CLASS);

    public static Object getCraftPlayer(Player player) {
        try {
            return CRAFT_PLAYER_CLASS.getMethod("getHandle").invoke(player);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
            e.printStackTrace();
        }

        return null;
    }

    public static Object getConnection(Object craftPlayer) {
        try {
            Field field = craftPlayer.getClass().getField("playerConnection");
            return field.get(craftPlayer);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendPacket(Player player, Packet packet) {
        sendPacket(player, packet.getPacket());
    }

    public static void sendPacket(Player player, Object packet) {
        Validate.notNull(player, "Player can't be null");
        Validate.notNull(packet, "Packet can't be null");

        SEND_PACKET_METHOD.invoke(getConnection(getCraftPlayer(player)), packet);
    }

    public static Object createPacket(String name, Class<?>[] parameters, Object[] objects) {
        Class<?> clazz = Reflections.getMinecraftClass(name);
        return Reflections.getConstructor(clazz, parameters).invoke(objects);
    }

    public static Object toJson(String text) {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("text", text);
            return CHAT_SERIALIZER.getMethod("a", String.class).invoke(null, jsonObject.toString());
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
