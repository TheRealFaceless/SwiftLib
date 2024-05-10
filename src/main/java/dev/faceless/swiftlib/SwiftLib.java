package dev.faceless.swiftlib;

import dev.faceless.swiftlib.lib.command.Command;
import dev.faceless.swiftlib.lib.storage.ConfigManager;
import dev.faceless.swiftlib.test.TestCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class SwiftLib extends JavaPlugin {
    public static JavaPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        ConfigManager.getManager().load(this);
        Command.register(this, new TestCommand());
    }
}