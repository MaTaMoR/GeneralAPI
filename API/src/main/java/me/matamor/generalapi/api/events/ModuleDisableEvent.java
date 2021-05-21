package me.matamor.generalapi.api.events;

import me.matamor.minesoundapi.modules.Module;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ModuleDisableEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Module module;

    public ModuleDisableEvent(Module module) {
        this.module = module;
    }

    public Module getModule() {
        return module;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
