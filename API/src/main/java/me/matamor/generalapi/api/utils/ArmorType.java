package me.matamor.generalapi.api.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import us.swiftex.custominventories.utils.CustomItem;

public enum ArmorType {

    HELMET(5),
    CHESTPLATE(6),
    LEGGINGS(7),
    BOOTS(8);

    private final int slot;

    ArmorType(int slot){
        this.slot = slot;
    }

    public int getSlot(){
        return slot;
    }

    public ItemStack get(Player player) {
        switch (this) {
            case HELMET:
                return player.getInventory().getHelmet();
            case CHESTPLATE:
                return player.getInventory().getChestplate();
            case LEGGINGS:
                return player.getInventory().getLeggings();
            case BOOTS:
                return player.getInventory().getBoots();
            default:
                return null;
        }
    }

    public static ArmorType matchType(ItemStack itemStack){
        if(itemStack == null) {
            return null;
        }

        return matchType(itemStack.getType());
    }

    public static ArmorType matchType(Material material){
        if(material == null) return null;

        switch (material) {
            case DIAMOND_HELMET:
            case GOLD_HELMET:
            case IRON_HELMET:
            case CHAINMAIL_HELMET:
            case LEATHER_HELMET:
                return HELMET;
            case DIAMOND_CHESTPLATE:
            case GOLD_CHESTPLATE:
            case IRON_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE:
            case LEATHER_CHESTPLATE:
                return CHESTPLATE;
            case DIAMOND_LEGGINGS:
            case GOLD_LEGGINGS:
            case IRON_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case LEATHER_LEGGINGS:
                return LEGGINGS;
            case DIAMOND_BOOTS:
            case GOLD_BOOTS:
            case IRON_BOOTS:
            case CHAINMAIL_BOOTS:
            case LEATHER_BOOTS:
                return BOOTS;
            default:
                return null;
        }
    }

    public void set(Player player, ItemStack itemStack) {
        switch (this) {
            case HELMET:
                player.getInventory().setHelmet(itemStack);
                break;
            case CHESTPLATE:
                player.getInventory().setChestplate(itemStack);
                break;
            case LEGGINGS:
                player.getInventory().setLeggings(itemStack);
                break;
            case BOOTS:
                player.getInventory().setBoots(itemStack);
                break;
        }
    }

    public void set(Player player, CustomItem customItem) {
        set(player, customItem.build(player));
    }

    public void remove(Player player) {
        set(player, (ItemStack) null);
    }

}