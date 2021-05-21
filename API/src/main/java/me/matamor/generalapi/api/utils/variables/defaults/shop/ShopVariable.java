package me.matamor.generalapi.api.utils.variables.defaults.shop;

import me.matamor.minesoundapi.shop.ShopEntry;
import me.matamor.minesoundapi.utils.variables.SimpleVariable;

public abstract class ShopVariable extends SimpleVariable<ShopEntry> {

    public ShopVariable(String name) {
        super(name);
    }
}
