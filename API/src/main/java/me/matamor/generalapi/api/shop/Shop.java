package me.matamor.generalapi.api.shop;

import me.matamor.minesoundapi.data.PlayerData;
import me.matamor.minesoundapi.inventory.InventoryBook;
import me.matamor.minesoundapi.shop.messages.ShopMessages;
import me.matamor.minesoundapi.utils.Buyable;
import org.bukkit.entity.Player;

public interface Shop<T extends Buyable> extends InventoryBook<ShopEntry<T>> {

    ShopMessages getMessages();

    void onSelect(T value, Player player, PlayerData playerData);

}
