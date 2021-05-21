package me.matamor.generalapi.api.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class InventoryUtils {

    private InventoryUtils() {

    }

    public static boolean canFit(ItemStack[] contents, ItemStack target) {
        return howMuchCanFit(contents, target) == target.getAmount();
    }

    //Check if an item can fit in the inventory

    public static int howMuchCanFit(ItemStack[] contents, ItemStack target) {
        int neededAmount = target.getAmount();

        for(ItemStack content : contents) {
            if(isValid(content)) {
                if (content.getMaxStackSize() > content.getAmount() && content.isSimilar(target)) {
                    if((neededAmount -= content.getAmount()) <= 0) return target.getAmount();
                }
            } else {
                if((neededAmount -= target.getMaxStackSize()) <= 0) return target.getAmount();
            }
        }

        return target.getAmount() - neededAmount;
    }

    //Try to add an item in the inventory, it will return the amount of items that couldn't be added

    public static int add(ItemStack[] contents, ItemStack target) {
        int neededAmount = target.getAmount();

        for(int i = 0; contents.length > i; i++) {
            ItemStack content = contents[i];

            if(isValid(content)) {
                if(content.getMaxStackSize() > content.getAmount() && content.isSimilar(target)) {
                    if(content.getAmount() + neededAmount <= content.getMaxStackSize()) {
                        content.setAmount(content.getAmount() + neededAmount);
                        return 0;
                    } else {
                        neededAmount -= (content.getMaxStackSize() - content.getAmount());
                        content.setAmount(content.getMaxStackSize());
                    }
                }
            } else {
                int toAdd;

                if(neededAmount >= target.getMaxStackSize()) {
                    toAdd = target.getMaxStackSize();
                } else {
                    toAdd = neededAmount;
                }

                ItemStack itemStack = target.clone();
                itemStack.setAmount(toAdd);

                contents[i] = itemStack;
                neededAmount -= toAdd;

                if(neededAmount <= 0) return neededAmount;
            }
        }

        return neededAmount;
    }

    //Check if the inventory contains certain, so if you provide a Diamond stack of 32, it will check if the inventory contains a total of 32 diamonds, separately.

    public static boolean contains(ItemStack[] contents, ItemStack target) {
        int neededAmount = target.getAmount();

        for (ItemStack content : contents) {
            if (isValid(content) && content.isSimilar(target)) {
                if ((neededAmount -= content.getAmount()) <= 0) return true;
            }
        }

        return false;
    }


    //It will try to remove certain item of the inventory and will return the amount of items that couldn't be removed

    public static int remove(ItemStack[] contents, ItemStack target) {
        int neededAmount = target.getAmount();

        for(int i = 0; contents.length > i; i++) {
            ItemStack content = contents[i];

            if (isValid(content) && content.isSimilar(target)) {
                if(neededAmount > content.getAmount()) {
                    neededAmount -= content.getAmount();
                    contents[i] = null;
                } else {
                    content.setAmount(content.getAmount() - neededAmount);
                    return 0;
                }
            }
        }

        return neededAmount;
    }


    //Checks how many times does the inventory contains certain item (it checks for the item with it's whole size)
    //So if you provide a Diamond stack of 32, it will check how many items it contains a Diamond stack of 32

    public static int containedAmount(ItemStack[] contents, ItemStack target) {
        int neededAmount = target.getAmount();
        int contained = 0;

        for (ItemStack content : contents) {
            if (isValid(content) && content.isSimilar(target)) {
                int amount = content.getAmount() / target.getAmount();

                if (amount > 0) {
                    contained += amount;
                }

                int toRemove = (content.getAmount() - ((amount > 0 ? target.getAmount() * amount : 0)));

                if (toRemove >= neededAmount) {
                    neededAmount = target.getAmount();
                    contained++;
                } else {
                    neededAmount -= toRemove;
                }
            }
        }

        return contained;
    }

    private static boolean isValid(ItemStack... itemStacks) { //A simple validation for the item
        for(ItemStack itemStack : itemStacks)
            if(itemStack == null || itemStack.getType() == Material.AIR) return false;

        return true;
    }
}
