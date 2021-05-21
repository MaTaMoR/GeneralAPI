package me.matamor.generalapi.api.commands;

import lombok.Getter;
import lombok.Setter;
import me.matamor.generalapi.api.APIPlugin;
import me.matamor.generalapi.api.commands.data.ICommandData;
import me.matamor.generalapi.api.commands.info.ICommandInfo;
import me.matamor.generalapi.api.utils.Validate;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseICommand<T extends APIPlugin, K extends ICommandData> implements ICommand<T, K> {

    private final Map<String, ICommand<T, K>> children = new LinkedHashMap<>();

    @Getter
    private final T plugin;

    @Getter
    private final ICommandInfo info;

    @Getter
    private ICommand<T, K> parent;

    @Getter @Setter
    private ICommand<T, K> helpCommand;

    @Getter @Setter
    private CommandStatus restriction;

    public BaseICommand(T plugin, ICommandInfo info) {
        Validate.notNull(plugin, "Plugin can't be null!");
        Validate.notNull(info, "Info can't be null!");

        this.plugin = plugin;
        this.info = info;
    }

    @Override
    public void setParent(ICommand<T, K> parent) {
        if (this.parent != null) {
            this.parent.removeChild(this);
        }

        this.parent = parent;

        if (this.parent != null) {
            this.parent.addChild(this);
        }
    }

    @Override
    public void addChild(ICommand<T, K> subCommand) {
        if (subCommand.getParent() != this) {
            subCommand.setParent(this);
        }

        this.children.put(subCommand.getName(), subCommand);
    }

    @Override
    public ICommand<T, K> getChild(String childName) {
        for (ICommand<T, K> child : this.children.values()) {
            if (child.getName().equalsIgnoreCase(childName)) {
                return child;
            }

            for (String alias : child.getInfo().getAliases()) {
                if (alias.equalsIgnoreCase(childName)) {
                    return child;
                }
            }
        }

        return null;
    }

    @Override
    public void removeChild(ICommand<T, K> child) {
        if (child.getParent() == this) {
            child.setParent(null);
        }

        this.children.remove(child.getName());
    }

    @Override
    public Collection<ICommand<T, K>> getChildren() {
        return this.children.values();
    }

    @Override
    public boolean hasRestriction() {
        return this.restriction != null;
    }
}
