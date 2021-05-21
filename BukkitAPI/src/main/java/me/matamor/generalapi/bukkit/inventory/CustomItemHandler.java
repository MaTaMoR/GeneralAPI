package me.matamor.generalapi.bukkit.inventory;

import me.matamor.custominventories.utils.CustomItem;
import me.matamor.minesoundapi.data.PlayerData;

public interface CustomItemHandler {

    CustomItem getItem(PlayerData playerData);

}
