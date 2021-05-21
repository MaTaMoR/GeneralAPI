package me.matamor.generalapi.bukkit.inventory;

import me.matamor.custominventories.icons.InventoryIcon;
import me.matamor.minesoundapi.data.PlayerData;

public interface InventoryPage extends InventoryHandler {

    InventoryBook getParent();

    int getPage();

    int actualPage();

    boolean hasPrevious();

    InventoryIcon getPrevious(PlayerData playerData);

    boolean hasNext();

    InventoryIcon getNext(PlayerData playerData);
}
