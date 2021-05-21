package me.matamor.generalapi.api.modules;

import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ModuleLogger extends Logger {

    @Getter
    private String prefix;

    public ModuleLogger(Module context) {
        super(context.getClass().getCanonicalName(), null);

        this.prefix = "[" + context.getModuleDescription().getName() + "]";

        setParent(Bukkit.getServer().getLogger());
        setLevel(Level.ALL);
    }

    @Override
    public void log(LogRecord logRecord) {
        logRecord.setMessage(this.prefix + logRecord.getMessage());
        super.log(logRecord);
    }
}