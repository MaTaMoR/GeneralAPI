package me.matamor.generalapi.api.commands;

import me.matamor.generalapi.api.APIPlugin;
import me.matamor.generalapi.api.commands.data.ICommandData;
import me.matamor.generalapi.api.commands.info.ICommandInfo;

import java.util.*;

public interface ICommand<T extends APIPlugin, K extends ICommandData> {

    T getPlugin();

    ICommandInfo getInfo();

    default String getName() {
        return getInfo().getName();
    }

    default String getUsage() {
        return getName() + (getInfo().hasArguments() ? " " + getInfo().getArguments() : "");
    }

    ICommand<T, K> getParent();

    void setRestriction(CommandStatus commandStatus);

    boolean hasRestriction();

    CommandStatus getRestriction();

    void setHelpCommand(ICommand<T, K> helpCommand);

    ICommand<T, K> getHelpCommand();

    void setParent(ICommand<T, K> parent);

    void addChild(ICommand<T, K> subCommands);

    ICommand<T, K> getChild(String childName);

    void removeChild(ICommand<T, K> child);

    Collection<ICommand<T, K>> getChildren();

    default boolean handleCommand(K commandData) {
        //Invalid command seder
        if (hasRestriction() && commandData.isValid(getRestriction())) {
            commandData.sendMessage("&cNo puedes usar este comando!");
            return false;
        }

        //Check MinArgs and MaxArgs
        if ((getInfo().hasMinArgs() && getInfo().getMinArgs() > commandData.getLength()) || (getInfo().hasMaxArgs() && getInfo().getMaxArgs() < commandData.getLength())) {
            commandData.sendMessage("&cUso incorrecto del comando!");
            commandData.sendMessage("&8Uso: &7" + getUsage());
            return false;
        }

        try {
            return onCommand(commandData) == CommandStatus.SUCCESS;
        } catch (ICommandException e) {
            commandData.sendMessage("&4Error: &c" + e.getMessage());
            return false;
        }
    }

    CommandStatus onCommand(K commandData) throws ICommandException;

    default String[] args(String[] args, int start) {
        return Arrays.copyOfRange(args, start, args.length);
    }
}
