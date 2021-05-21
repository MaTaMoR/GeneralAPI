package me.matamor.generalapi.bukkit.commands;

import me.matamor.generalapi.api.APIPlugin;
import me.matamor.generalapi.api.commands.BaseICommand;
import me.matamor.generalapi.api.commands.info.ICommandInfo;

public abstract class BukkitICommand<T extends APIPlugin> extends BaseICommand<T, BukkitICommandData> {

    public BukkitICommand(T plugin, ICommandInfo info) {
        super(plugin, info);
    }
}
