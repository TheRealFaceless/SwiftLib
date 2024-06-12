package dev.faceless.swiftlib;

import dev.faceless.swiftlib.lib.events.GlobalEventHandler;
import dev.faceless.swiftlib.lib.listeners.MenuListener;
import dev.faceless.swiftlib.lib.listeners.PaginatedMenuListener;
import dev.faceless.swiftlib.lib.region.RegionManager;
import dev.faceless.swiftlib.lib.storage.yaml.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import java.util.logging.Logger;

@SuppressWarnings("unused")
public final class SwiftLib {
    private static JavaPlugin plugin;
    private static boolean debugMode = false;

    private SwiftLib(){}

    public static void onEnable(JavaPlugin plugin) {
        SwiftLib.plugin = plugin;
        ConfigManager.getManager().load(plugin);
        RegionManager.loadAll();

        GlobalEventHandler.get()
                .addListener(new PaginatedMenuListener())
                .addListener(new MenuListener());
    }

    public static void onDisable(JavaPlugin plugin) {
        RegionManager.saveAll();
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