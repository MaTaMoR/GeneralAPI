package me.matamor.generalapi.api.commands;

public enum CommandStatus {

    /** The command executed successfully */
    SUCCESS,
    /** The command did not execute correctly */
    FAILED,
    /** User supplied bad command arguments */
    BAD_ARGS,
    /** Command is intended to be run by players only */
    PLAYER_ONLY("players"),
    /** Command is intended to be run by the console only */
    CONSOLE_ONLY("the console"),
    /** Command is intended to be run by remote consoles (rcon) only */
    RCON_ONLY("remote consoles"),
    /** Command is intended to be run by command blocks only */
    COMMAND_BLOCK_ONLY("command blocks"),
    /** Command is intended to be run by a CommandMinecart only */
    MINECART_ONLY("minecarts"),
    /** Command should not be executed by a proxied sender */
    NO_PROXIES,
    /** The user does not have the appropriate permissions to execute this */
    NO_PERMISSION,
    /** The command is not meant to be an endpoint */
    NOT_EXECUTABLE;

    private final Object[] args;

    CommandStatus(Object... args) {
        this.args = args;
    }
}