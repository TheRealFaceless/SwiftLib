package dev.faceless.swiftlib;

import dev.faceless.swiftlib.lib.events.GlobalEventHandler;
import dev.faceless.swiftlib.lib.listeners.MenuListener;
import dev.faceless.swiftlib.lib.listeners.PaginatedMenuListener;
import dev.faceless.swiftlib.lib.storage.yaml.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import java.util.logging.Logger;

@SuppressWarnings("unused")
public class SwiftLib {
    private static JavaPlugin plugin;
    private static boolean debugMode = true;

    public void onEnable(JavaPlugin plugin) {
        SwiftLib.plugin = plugin;
        ConfigManager.getManager().load(plugin);

        GlobalEventHandler.get()
                .addListener(new PaginatedMenuListener())
                .addListener(new MenuListener());
    }

    public void onDisable(JavaPlugin plugin) {
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

    public static void setDebugMode(boolean debugMode) {
        SwiftLib.debugMode = debugMode;
    }

    public static void setPlugin(JavaPlugin plugin) {
        SwiftLib.plugin = plugin;
    }
}