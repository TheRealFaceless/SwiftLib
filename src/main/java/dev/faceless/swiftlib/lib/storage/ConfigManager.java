package dev.faceless.swiftlib.lib.storage;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;

@CanIgnoreReturnValue
@SuppressWarnings("unused")
public class ConfigManager {

    private final Map<String, Config> configs = new HashMap<>();
    private final Set<String> blacklistedPaths = new HashSet<>();
    private static ConfigManager manager;

    private ConfigManager(){}

    public static ConfigManager getManager() {
        return manager == null ? manager = new ConfigManager() : manager;
    }

    public void load(JavaPlugin plugin) {
        File dataFolder = plugin.getDataFolder();
        if (dataFolder.exists() && dataFolder.isDirectory()) {
            loadConfigsFromFolder(dataFolder, plugin, new HashSet<>());
        }
    }

    public void register(String path, Config config) {
        configs.put(path, config);
    }

    public void createConfig(String path, JavaPlugin plugin) {
        new Config(path, plugin);
    }

    private void loadConfigsFromFolder(File folder, JavaPlugin plugin, Set<File> loadedFiles) {
        File[] files = folder.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                loadConfigsFromFolder(file, plugin, loadedFiles);
            }else if (file.getName().endsWith(".yml") && !loadedFiles.contains(file)) {
                String relativePath = file.getPath().replace("plugins" + File.separator + plugin.getName() + File.separator, "");

                if (isPathBlacklisted(relativePath, blacklistedPaths)) continue;
                createConfig(relativePath, plugin);
                loadedFiles.add(file);
            }
        }
    }

    private boolean isPathBlacklisted(String path, Set<String> blacklistedPaths) {
        for (String blacklistedPath : blacklistedPaths) {
            if (path.startsWith(blacklistedPath)) return true;
        }
        return false;
    }

    public void blacklistPath(String path) {
        blacklistedPaths.add(path);
    }

    public void removeBlacklistedPath(String path) {
        blacklistedPaths.remove(path);
    }

    public Config createOrGetConfig(String path, JavaPlugin plugin){
        if (path == null) throw new IllegalArgumentException("Path cannot be null");
        if(getConfig(path) != null) return getConfig(path);

        return new Config(path, plugin);
    }

    @Nullable
    public Config getConfig(String path) {
        if(configs.get(path) != null) return configs.get(path);
        if (!path.endsWith(".yml")) path += ".yml";
        return configs.get(path);
    }

    public void reload(String path) {
        Config config = getConfig(path);
        if (config != null) {
            config.reload();
        }
    }

    public void sendNames() {
        configs.keySet().forEach(name -> Bukkit.getConsoleSender().sendMessage(name));
        configs.values().forEach(config -> Bukkit.getConsoleSender().sendMessage(config.getConfigFile().getAbsolutePath()));
    }

    public void saveAll() {
        configs.values().forEach(Config::save);
    }

    public void reloadAll() {
        configs.values().forEach(Config::reload);
    }

    public List<String> getConfigs() {
        return new ArrayList<>(configs.keySet());
    }
}
