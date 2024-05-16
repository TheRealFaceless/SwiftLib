package dev.faceless.swiftlib;

import dev.faceless.swiftlib.lib.command.Command;
import dev.faceless.swiftlib.lib.events.GlobalEventHandler;
import dev.faceless.swiftlib.lib.listeners.MenuListener;
import dev.faceless.swiftlib.lib.listeners.PaginatedMenuListener;
import dev.faceless.swiftlib.lib.storage.yaml.ConfigManager;
import dev.faceless.swiftlib.test.TestCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import java.util.logging.Logger;

public class SwiftLib extends JavaPlugin {
    private static JavaPlugin plugin;
    private static final boolean debugMode = true;

    @Override
    public void onEnable() {
        plugin = this;

        ConfigManager.getManager().blacklistPath("maps\\");
        ConfigManager.getManager().load(plugin);

        GlobalEventHandler.get()
                .addListener(new PaginatedMenuListener())
                .addListener(new MenuListener());

        Command.register(plugin, new TestCommand());
    }

    @Override
    public void onDisable() {
        ConfigManager.getManager().saveAll();
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static Logger getPluginLogger() {
        return plugin.getLogger();
    }

    public static File getPluginDataFolder() {
        return plugin.getDataFolder();
    }
}