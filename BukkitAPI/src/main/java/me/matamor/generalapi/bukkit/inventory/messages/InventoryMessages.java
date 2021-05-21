package me.matamor.generalapi.bukkit.inventory.messages;

import me.matamor.minesoundapi.settings.SimpleSettings;
import org.bukkit.plugin.Plugin;

public class InventoryMessages extends SimpleSettings<InventoryMessage> {

    public InventoryMessages(Plugin plugin, String path) {
        super(plugin, path);
    }

    public final InventoryMessage TITLE = register(new InventoryMessage(this, "Messages.Title", "&3Inventario"));
    public final InventoryMessage PAGE_TITLE = register(new InventoryMessage(this, "Messages.PageTitle", "&8Pagina {inventory_page}"));
    public final InventoryMessage RETURN = register(new InventoryMessage(this, "Messages.Return", "&7Clica para volver!"));
    public final InventoryMessage PREVIOUS_PAGE = register(new InventoryMessage(this, "Messages.PreviousPage", "&7Pagina {inventory_previous_page}"));
    public final InventoryMessage NEXT_PAGE = register(new InventoryMessage(this, "Messages.NextPage", "&7Pagina {inventory_next_page}"));

}
