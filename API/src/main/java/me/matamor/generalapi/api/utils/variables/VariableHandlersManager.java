package me.matamor.generalapi.api.utils.variables;

import me.matamor.minesoundapi.utils.variables.defaults.menu.MenuVariableHandler;
import me.matamor.minesoundapi.utils.variables.defaults.player.PlayerVariableHandler;
import me.matamor.minesoundapi.utils.variables.defaults.shop.ShopVariableHandler;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VariableHandlersManager {

    private static final Map<Class<? extends VariableHandler>, VariableHandler> variables = new HashMap<>();

    public static final MenuVariableHandler MENU_VARIABLES = registerHandler(new MenuVariableHandler());
    public static final PlayerVariableHandler PLAYER_VARIABLES = registerHandler(new PlayerVariableHandler());
    public static final ShopVariableHandler SHOP_VARIABLES = registerHandler(new ShopVariableHandler());

    public static <T extends VariableHandler> T registerHandler(T handler) {
        if (variables.containsKey(handler.getClass())) throw new RuntimeException("The variable plugin " + handler.getClass().getSimpleName() + " is already registered");
        variables.put(handler.getClass(), handler);

        return handler;
    }

    public static void registerHandlers(VariableHandler... values) {
        for (VariableHandler variableHandler : values) {
            registerHandler(variableHandler);
        }
    }

    public static <T extends VariableHandler> T getHandler(Class<T> clazz) {
        VariableHandler variable = variables.get(clazz);
        return (variable == null ? null : (T) variable);
    }

    public static void unregisterVariable(Class<? extends VariableHandler> clazz) {
        variables.remove(clazz);
    }

    public static Collection<VariableHandler> getVariables() {
        return Collections.unmodifiableCollection(variables.values());
    }
}
