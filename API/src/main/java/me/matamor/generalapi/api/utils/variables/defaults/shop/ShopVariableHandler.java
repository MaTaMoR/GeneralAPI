package me.matamor.generalapi.api.utils.variables.defaults.shop;

import me.matamor.minesoundapi.shop.ShopEntry;
import me.matamor.minesoundapi.utils.variables.SimpleVariableHandler;
import me.matamor.minesoundapi.utils.variables.Variable;

public class ShopVariableHandler extends SimpleVariableHandler<ShopVariable, ShopEntry> {

    public final Variable<ShopEntry> NAME = register(new ShopVariable("{shop_entry_name}") {
        @Override
        public String getReplacement(ShopEntry object) {
            return object.getEntry().getName();
        }
    });

}
