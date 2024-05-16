package dev.faceless.swiftlib;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        SwiftLib.onEnable(this);
    }

    @Override
    public void onDisable() {
        SwiftLib.onDisable(this);
    }
}
