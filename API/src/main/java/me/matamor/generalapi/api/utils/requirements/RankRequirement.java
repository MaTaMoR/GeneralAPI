package me.matamor.generalapi.api.utils.requirements;

import lombok.Getter;
import me.matamor.minesoundapi.data.PlayerData;
import me.matamor.minesoundapi.playersettings.PlayerSettings;
import me.matamor.minesoundapi.playersettings.Rank;
import org.bukkit.entity.Player;

public class RankRequirement extends SimpleExtraRequirement {

    @Getter
    private final Rank rank;

    public RankRequirement(String requirementMessage, Rank rank) {
        super(requirementMessage);

        this.rank = rank;
    }

    @Override
    public boolean hasRequirement(Player player, PlayerData playerData) {
        return playerData.getData(PlayerSettings.class).getRank().isHigherEqual(this.rank);
    }
}
