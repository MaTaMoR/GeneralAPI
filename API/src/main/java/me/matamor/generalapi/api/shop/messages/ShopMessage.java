package me.matamor.generalapi.api.shop.messages;

import me.matamor.minesoundapi.settings.defaults.StringSetting;
import me.matamor.minesoundapi.shop.ShopEntry;
import me.matamor.minesoundapi.utils.Utils;
import me.matamor.minesoundapi.utils.variables.VariableHandlersManager;

public class ShopMessage extends StringSetting {

    private final ShopMessages parent;

    public ShopMessage(ShopMessages parent, String path, String defaultSetting) {
        super(path, defaultSetting);

        this.parent = parent;
    }

    public ShopMessages getParent() {
        return parent;
    }

    public String replace(ShopEntry entry) {
        return Utils.color(VariableHandlersManager.SHOP_VARIABLES.replace(entry, get()));
    }
}
