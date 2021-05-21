package me.matamor.generalapi.bukkit.inventory;

import me.matamor.custominventories.icons.InventoryIcon;
import me.matamor.minesoundapi.data.PlayerData;
import org.bukkit.entity.Player;

public interface IconHandler {

    InventoryIcon getIcon(Player player, PlayerData playerData);

}
