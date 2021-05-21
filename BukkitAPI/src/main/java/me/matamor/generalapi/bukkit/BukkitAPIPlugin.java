package me.matamor.generalapi.bukkit;

import me.matamor.generalapi.api.APIPlugin;
import me.matamor.generalapi.api.commands.BaseICommand;
import me.matamor.generalapi.api.commands.CommandStatus;
import me.matamor.generalapi.api.commands.ICommand;
import me.matamor.generalapi.api.commands.ICommandException;
import me.matamor.generalapi.api.commands.data.ICommandData;
import me.matamor.generalapi.api.commands.info.ICommandInfo;
import me.matamor.generalapi.api.commands.register.ICommandRegister;
import me.matamor.generalapi.bukkit.commands.register.BukkitICommandRegister;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitAPIPlugin extends JavaPlugin implements APIPlugin {

    private final ICommandRegister commandRegister = new BukkitICommandRegister();

    @Override
    @SuppressWarnings("unchecked")
    public void onEnable() {
        ICommand actualCommand = new BaseICommand(this, new ICommandInfo("test")) {
            @Override
            public CommandStatus onCommand(ICommandData commandData) throws ICommandException {
                commandData.sendMessage("Hola cabrones :D");
                return CommandStatus.SUCCESS;
            }
        };

        this.commandRegister.register(actualCommand);
    }
}
