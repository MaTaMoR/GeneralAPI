package me.matamor.generalapi.bukkit.inventory;

import me.matamor.custominventories.inventories.CustomInventory;
import me.matamor.minesoundapi.data.PlayerData;
import org.bukkit.entity.Player;

public interface InventoryHandler {

    CustomInventory getInventory(Player player, PlayerData playerData);

}
