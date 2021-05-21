package me.matamor.generalapi.api.utils.variables.defaults.player;

import me.matamor.minesoundapi.data.PlayerData;
import me.matamor.minesoundapi.utils.variables.SimpleVariableHandler;
import me.matamor.minesoundapi.utils.variables.Variable;

public class PlayerVariableHandler extends SimpleVariableHandler<PlayerVariable, PlayerData> {

    public final Variable<PlayerData> NAME = register(new PlayerVariable("{name}") {
        @Override
        public String getReplacement(PlayerData object) {
            return object.getIdentifier().getName();
        }
    });
}
