package me.matamor.generalapi.bukkit.commands;

import lombok.Getter;
import me.matamor.generalapi.api.commands.CommandStatus;
import me.matamor.generalapi.api.commands.data.SimpleICommandData;
import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.CommandMinecart;

public class BukkitICommandData extends SimpleICommandData {

    @Getter
    private final CommandSender sender;

    public BukkitICommandData(String[] args, CommandSender sender) {
        super(args);

        this.sender = sender;
    }

    public boolean isPlayer() {
        return this.sender instanceof Player;
    }

    public Player getPlayer() {
        return (Player) this.sender;
    }

    public Player getPlayer(int position) {
        String name = getString(position);
        return Bukkit.getServer().getPlayer(name);
    }

    @Override
    public boolean isValid(CommandStatus commandStatus) {
        switch (commandStatus) {
            case PLAYER_ONLY:
                return sender instanceof Player;
            case CONSOLE_ONLY:
                return sender instanceof ConsoleCommandSender;
            case RCON_ONLY:
                return sender instanceof RemoteConsoleCommandSender;
            case COMMAND_BLOCK_ONLY:
                return sender instanceof BlockCommandSender;
            case MINECART_ONLY:
                return sender instanceof CommandMinecart;
            default:
                return true;
        }
    }

    @Override
    public void sendMessage(String message) {
        this.sender.sendMessage(message);
    }
}
