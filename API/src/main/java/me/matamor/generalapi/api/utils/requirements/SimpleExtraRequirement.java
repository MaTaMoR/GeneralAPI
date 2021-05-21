package me.matamor.generalapi.api.utils.requirements;

import me.matamor.custominventories.requirements.SimpleRequirement;
import me.matamor.minesoundapi.MineSoundAPI;
import me.matamor.minesoundapi.data.PlayerData;
import org.bukkit.entity.Player;

public abstract class SimpleExtraRequirement extends SimpleRequirement implements ExtraRequirement {

    public SimpleExtraRequirement(String requirementMessage) {
        super(requirementMessage);
    }
}
