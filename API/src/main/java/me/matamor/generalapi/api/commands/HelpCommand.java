package me.matamor.generalapi.api.commands;

import me.matamor.generalapi.api.APIPlugin;
import me.matamor.generalapi.api.commands.data.ICommandData;

import java.util.Collection;

public abstract class HelpCommand<T extends APIPlugin, K> implements ICommand<T, K> {

    @Override
    public void onCommand(ICommandData<K> commandArgs) {
        Collection<ICommand<T, K>> children = getParent().getChildren();

        if (children.isEmpty()) {
            commandArgs.sendMessage("&cNo hay ningun comando!");
        } else {
            commandArgs.sendMessage("&8-------------------- &8[&aAyuda&8] &8--------------------");

            children.forEach(subCommand -> commandArgs.sendMessage("&a%s &7%s", subCommand.getInfo().getArguments(),
                    subCommand.getInfo().getDescription()));
        }
    }
}
