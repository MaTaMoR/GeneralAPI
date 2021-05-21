package me.matamor.generalapi.api.commands.register;

import me.matamor.generalapi.api.commands.ICommand;

public interface ICommandRegister<T extends ICommand> {

    boolean register(T command);

}
