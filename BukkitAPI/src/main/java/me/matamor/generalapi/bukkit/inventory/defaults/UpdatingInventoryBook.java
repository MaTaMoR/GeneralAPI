package me.matamor.generalapi.bukkit.inventory.defaults;

import lombok.Getter;
import me.matamor.custominventories.inventories.CustomInventory;
import me.matamor.custominventories.utils.viewer.Pagination;
import me.matamor.minesoundapi.data.PlayerData;
import me.matamor.minesoundapi.inventory.IconHandler;
import me.matamor.minesoundapi.inventory.InventoryBook;
import me.matamor.minesoundapi.inventory.InventoryPage;
import me.matamor.minesoundapi.inventory.messages.InventoryMessages;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class UpdatingInventoryBook implements InventoryBook {

    @Getter
    private final InventoryMessages messages;

    @Getter
    private final Pagination<IconHandler> pagination;

    @Getter
    private final TimeUnit timeUnit;

    @Getter
    private final long time;

    public UpdatingInventoryBook(InventoryMessages messages, TimeUnit timeUnit, long time, Collection<IconHandler> iconHandlers) {
        this.messages = messages;
        this.pagination = new Pagination<>(25, iconHandlers);

        this.timeUnit = timeUnit;
        this.time = time;
    }

    @Override
    public InventoryPage getPage(int page) {
        if (!this.pagination.exists(page)) {
            throw new IndexOutOfBoundsException("Index: " + page + ", Size: " + this.pagination.totalPages());
        }

        return new SimpleInventoryPage(this, page);
    }

    @Override
    public CustomInventory getInventory(Player player, PlayerData playerData) {
        return getPage(0).getInventory(player, playerData);
    }
}