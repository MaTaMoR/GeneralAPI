package me.matamor.generalapi.api.commands.data;

import lombok.Getter;

public abstract class SimpleICommandData implements ICommandData {

    @Getter
    private final String[] args;

    public SimpleICommandData(String[] args) {
        this.args = args;
    }

    @Override
    public int getLength() {
        return this.args.length;
    }
}
