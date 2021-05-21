package me.matamor.generalapi.api.shop.defaults;

import me.matamor.minesoundapi.data.PlayerData;
import me.matamor.minesoundapi.entries.defaults.ListDataEntry;
import me.matamor.minesoundapi.shop.Shop;
import me.matamor.minesoundapi.shop.ShopEntry;
import me.matamor.minesoundapi.utils.Buyable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import us.swiftex.custominventories.actions.ClickAction;
import us.swiftex.custominventories.icons.Icon;
import us.swiftex.custominventories.utils.CustomItem;

import java.util.ArrayList;
import java.util.List;

public class SimpleShopEntry<T extends Buyable> implements ShopEntry<T> {

    private static final String EMPTY_LINE = "";

    private final Shop parent;
    private final T entry;

    public SimpleShopEntry(Shop parent, T entry) {
        this.parent = parent;
        this.entry = entry;
    }

    @Override
    public Shop getParent() {
        return parent;
    }

    @Override
    public T getEntry() {
        return entry;
    }

    @Override
    public Icon getIcon(final PlayerData playerData) {
        return new Icon() {
            @Override
            public CustomItem getItem(Player player) {
                ListDataEntry<T> dataEntry = getEntry().getData(playerData);

                CustomItem customItem = getEntry().getItem(playerData).clone();
                List<String> lore = new ArrayList<>(customItem.getLore());

                if(getEntry().getRequirement().hasRequirement(player, playerData)) {
                    if(dataEntry.has(getEntry().getName())) {
                        if(getEntry().isActive(player, playerData)) {
                            customItem.setItemGlow(true);
                            lore.add(getParent().getMessages().ACTIVE.replace(SimpleShopEntry.this));
                        } else {
                            lore.add(getParent().getMessages().OWNED.replace(SimpleShopEntry.this));
                        }
                    } else if(getEntry().isFree()) {
                        lore.add(getParent().getMessages().FREE.replace(SimpleShopEntry.this));
                    } else if(getEntry().canBeBought()) {
                        if(getEntry().canAfford(player, playerData)) {
                            lore.add(getParent().getMessages().BUY.replace(SimpleShopEntry.this));
                        } else {
                            lore.add(getParent().getMessages().CAN_NOT_AFFORD.replace(SimpleShopEntry.this));
                        }
                    } else {
                        customItem.setMaterial(Material.INK_SACK);
                        customItem.setDataValue((short) 8);

                        lore.add(getParent().getMessages().CAN_NOT_BE_BOUGHT.replace(SimpleShopEntry.this));
                    }
                } else {
                    customItem.setMaterial(Material.INK_SACK);
                    customItem.setDataValue((short) 8);

                    lore.add(entry.getRequirement().getRequirementMessage());
                }

                customItem.setLore(lore);
                return customItem;
            }
        }.addClickAction(new ClickAction() {
            @Override
            public void execute(Player player) {
                ListDataEntry<T> dataEntry = getEntry().getData(playerData);

                if(getEntry().getRequirement().hasRequirement(player, playerData)) {
                    if(dataEntry.has(getEntry().getName())) {
                        getParent().onSelect(getEntry(), player, playerData);
                        getParent().getInventory(playerData).openInventory(player);
                    } else if(getEntry().isFree()) {
                        getEntry().getData(playerData).add(entry);

                        player.sendMessage(getParent().getMessages().ON_GET_FREE.replace(SimpleShopEntry.this));

                        getParent().getInventory(playerData).openInventory(player);
                    } else if(getEntry().canAfford(player, playerData)) {
                        if(getEntry().buy(player, playerData)) {
                            getEntry().getData(playerData).add(entry);

                            player.sendMessage(getParent().getMessages().ON_BUY.replace(SimpleShopEntry.this));

                            getParent().getInventory(playerData).openInventory(player);
                        } else {
                            player.sendMessage(getParent().getMessages().ERROR_ON_BUY.replace(SimpleShopEntry.this));
                        }
                    }
                }
            }
        });
    }

}
