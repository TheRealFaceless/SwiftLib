package dev.faceless.swiftlib;

import dev.faceless.swiftlib.lib.events.GlobalEventHandler;
import dev.faceless.swiftlib.lib.listeners.AbstractItemListener;
import dev.faceless.swiftlib.lib.listeners.MenuListener;
import dev.faceless.swiftlib.lib.listeners.PaginatedMenuListener;
import dev.faceless.swiftlib.lib.region.RegionManager;
import dev.faceless.swiftlib.lib.storage.yaml.ConfigManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import java.util.logging.Logger;

//TODO: FIX BOARD, ADD BRIGADIER WRAPPER, COMPLETE TEXTURE PACK API, Add SwapHand event to Abstract Item class.
@SuppressWarnings("unused")
public final class SwiftLib {
    @Getter private static JavaPlugin plugin;
    @Getter private static boolean debugMode = false;
    @Getter private static boolean loadRegions = false;

    private SwiftLib(){}

    public static void onEnable(JavaPlugin plugin) {
        SwiftLib.plugin = plugin;
        ConfigManager.getManager().load(plugin);
        if(loadRegions) RegionManager.loadAll();

        AbstractItemListener.init();
        GlobalEventHandler.get()
                .addListener(new PaginatedMenuListener())
                .addListener(new MenuListener());
    }

    public static void onDisable(JavaPlugin plugin) {
        RegionManager.saveAll();
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

    public static void setLoadRegions(boolean loadRegions) {
        SwiftLib.loadRegions = loadRegions;
    }

    public static void setPlugin(JavaPlugin plugin) {
        SwiftLib.plugin = plugin;
    }
}