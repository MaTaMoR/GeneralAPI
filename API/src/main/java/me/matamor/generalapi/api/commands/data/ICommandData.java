package me.matamor.generalapi.api.commands.data;

import me.matamor.generalapi.api.commands.CommandStatus;
import me.matamor.generalapi.api.commands.ICommandException;
import me.matamor.generalapi.api.utils.CastUtils;

import java.util.stream.IntStream;

public interface ICommandData {

    int getLength();

    String[] getArgs();

    boolean isValid(CommandStatus commandStatus);

    void sendMessage(String message);

    default void sendMessage(String message, Object... objects) {
        sendMessage(String.format(message, objects));
    }

    default int getInt(int position) {
        try {
            return CastUtils.asInt(getString(position));
        } catch (CastUtils.FormatException e) {
            throw new ICommandException("Invalid boolean!");
        }
    }

    default double getDouble(int position) {
        try {
            return CastUtils.asDouble(getString(position));
        } catch (CastUtils.FormatException e) {
            throw new ICommandException("Invalid boolean!");
        }
    }

    default boolean getBoolean(int position) {
        try {
            return CastUtils.asBoolean(getString(position));
        } catch (CastUtils.FormatException e) {
            throw new ICommandException("Invalid boolean!");
        }
    }

    default String getString(int position) {
        String[] args = getArgs();
        if (position < 0 || position >= args.length) {
            throw new ICommandException("Index: "+ position +", Size: " + args.length);
        }

        return args[position];
    }

    default String join() {
        return join(" ", 0);
    }

    default String join(String split, int start) {
        String[] args = getArgs();
        StringBuilder stringBuilder = new StringBuilder();

        IntStream.range(start, args.length)
                .forEach(i -> stringBuilder.append(args[i]).append(split));

        return stringBuilder.toString().trim();
    }
}
