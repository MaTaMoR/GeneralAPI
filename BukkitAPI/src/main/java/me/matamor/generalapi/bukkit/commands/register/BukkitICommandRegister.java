package me.matamor.generalapi.bukkit.commands.register;

import lombok.Getter;
import me.matamor.generalapi.api.commands.register.ICommandRegister;
import me.matamor.generalapi.bukkit.commands.BukkitICommand;
import me.matamor.generalapi.bukkit.commands.BukkitICommandData;
import me.matamor.generalapi.bukkit.utils.Reflections;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class BukkitICommandRegister implements ICommandRegister<BukkitICommand> {

    private static final CommandMap COMMANDS = (CommandMap) Reflections.getMethod(Bukkit.getServer().getClass(), "getCommandMap").invoke(Bukkit.getServer());

    @Override
    public boolean register(BukkitICommand command) {
        Command actualCommand = COMMANDS.getCommand(command.getName());

        //Command is already registered!
        if (actualCommand instanceof BukkitCommand && ((BukkitCommand) actualCommand).getCommand() == command) {
            return true;
        } else {
            return COMMANDS.register(command.getName(), new BukkitCommand(command));
        }
    }

    private class BukkitCommand extends Command {

        @Getter
        private final BukkitICommand command;

        private BukkitCommand(BukkitICommand command) {
            super(command.getName(), command.getInfo().getDescription(), command.getUsage(), Arrays.asList(command.getInfo().getAliases()));

            this.command = command;
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean execute(CommandSender commandSender, String label, String[] args) {
            return this.command.handleCommand(new BukkitICommandData(args, commandSender));
        }
    }
}
