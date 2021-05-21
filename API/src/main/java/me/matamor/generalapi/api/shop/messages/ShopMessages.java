package me.matamor.generalapi.api.shop.messages;

import me.matamor.minesoundapi.settings.SimpleSettings;
import org.bukkit.plugin.Plugin;

public class ShopMessages extends SimpleSettings<ShopMessage> {

    public ShopMessages(Plugin plugin, String path) {
        super(plugin, path);
    }

    public final ShopMessage BUY = register(new ShopMessage(this, "Messages.Get", "&bClica para comprarlo!"));
    public final ShopMessage FREE = register(new ShopMessage(this, "Messages.Free", "&bClica para conseguirlo!"));
    public final ShopMessage CAN_NOT_AFFORD = register(new ShopMessage(this, "Messages.NoMoney", "&cNo tienes suficiente dinero!"));
    public final ShopMessage OWNED = register(new ShopMessage(this, "Messages.Owned", "&6Lo tienes!"));
    public final ShopMessage ACTIVE = register(new ShopMessage(this, "Messages.Active", "&bSeleccionado!"));
    public final ShopMessage ON_BUY = register(new ShopMessage(this, "Messages.OnBuy", "&aHas comprado &b{shop_entry_name}"));
    public final ShopMessage ON_GET_FREE = register(new ShopMessage(this, "Messages.OnBuy", "&b{shop_entry_name} &aahora es tuyo!"));
    public final ShopMessage ERROR_ON_BUY = register(new ShopMessage(this, "Messages.ErrorOnBuy", "&cHa sucedido un error al realizar la compra!"));
    public final ShopMessage CAN_NOT_BE_BOUGHT = register(new ShopMessage(this, "Messages.CanNotBeBought", "&cNo se puede comprar, consiguelo en otro lado!"));

}
