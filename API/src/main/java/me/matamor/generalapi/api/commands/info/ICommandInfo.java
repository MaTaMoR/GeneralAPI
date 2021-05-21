package me.matamor.generalapi.api.commands.info;

import lombok.Getter;
import me.matamor.generalapi.api.utils.Validate;

public class ICommandInfo {

    @Getter
    private final String name;

    @Getter
    private String description = "";

    @Getter
    private String[] aliases = new String[0];

    @Getter
    private String arguments;

    @Getter
    private int minArgs = -1;

    @Getter
    private int maxArgs = -1;

    public ICommandInfo(String name) {
        Validate.notNull(name, "Name can't be null!");

        this.name = name;


        new ICommandInfo("reload")
                .setDescription("A command to reload the server!")
                .setAliases(new String[] { "rl" })
                .setArguments("<force>"); //El argumento serian los 'args', por ejemplo 'reload <force>' y el force fuera otra cosa
    }

    public ICommandInfo setDescription(String description) {
        Validate.notNull(description, "Description can't be null!");

        this.description = description;

        return this;
    }

    public ICommandInfo setAliases(String[] aliases) {
        Validate.notNull(aliases, "Aliases can't be null!");

        this.aliases = aliases;

        return this;
    }

    public boolean hasArguments() {
        return this.arguments != null;
    }

    public ICommandInfo setArguments(String arguments) {
        Validate.notNull(arguments, "Arguments can't be null!");

        this.arguments = arguments;

        return this;
    }

    public boolean hasMinArgs() {
        return this.minArgs != -1;
    }

    public ICommandInfo setMaxArgs(int maxArgs) {
        Validate.isTrue(maxArgs > 0, "MaxArgs must be bigger than 0!");

        this.maxArgs = maxArgs;

        return this;
    }

    public boolean hasMaxArgs() {
        return this.maxArgs != -1;
    }

    public ICommandInfo setMinArgs(int minArgs) {
        Validate.isTrue(minArgs > 0, "MinArgs must be bigger than 0!");

        this.minArgs = minArgs;

        return this;
    }
}

