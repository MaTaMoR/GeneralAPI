package me.matamor.generalapi.api.utils;

import me.matamor.minesoundapi.data.PlayerData;
import me.matamor.minesoundapi.entries.defaults.ListDataEntry;
import me.matamor.minesoundapi.inventory.CustomItemHandler;
import org.bukkit.entity.Player;

public interface Buyable extends Name, CustomItemHandler {

    boolean isFree();

    Requirement getRequirement();

    boolean canBeBought();

    boolean canAfford(Player player, PlayerData playerData);

    boolean buy(Player player, PlayerData playerData);

    boolean isActive(Player player, PlayerData playerData);

    <T extends Buyable> ListDataEntry<T> getData(PlayerData playerData);

}
