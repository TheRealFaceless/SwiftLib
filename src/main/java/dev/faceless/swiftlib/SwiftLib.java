package dev.faceless.swiftlib;

import dev.faceless.swiftlib.lib.command.Command;
import dev.faceless.swiftlib.lib.events.GlobalEventHandler;
import dev.faceless.swiftlib.lib.storage.database.Database;
import dev.faceless.swiftlib.lib.storage.database.DatabaseType;
import dev.faceless.swiftlib.lib.storage.yaml.ConfigManager;
import dev.faceless.swiftlib.test.TestCommand;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class SwiftLib extends JavaPlugin {
    private static JavaPlugin plugin;
    private static final boolean debugMode = true;

    @Override
    public void onEnable() {
        plugin = this;

        ConfigManager.getManager().blacklistPath("maps\\");
        ConfigManager.getManager().load(plugin);
        Command.register(plugin, new TestCommand());
        try {
            // database example
            Database.createDbFile("data/players/test", DatabaseType.SQLITE, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // listener example
        GlobalEventHandler.get().addListener(PlayerBedLeaveEvent.class, (playerBedLeaveEvent -> {}));
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