package me.matamor.generalapi.api.utils.variables.defaults.player;

import me.matamor.minesoundapi.data.PlayerData;
import me.matamor.minesoundapi.utils.variables.SimpleVariable;

public abstract class PlayerVariable extends SimpleVariable<PlayerData> {

    public PlayerVariable(String name) {
        super(name);
    }
}
