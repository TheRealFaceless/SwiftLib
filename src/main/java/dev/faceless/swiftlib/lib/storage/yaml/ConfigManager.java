package dev.faceless.swiftlib.lib.storage.yaml;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.faceless.swiftlib.SwiftLib;
import dev.faceless.swiftlib.lib.text.TextUtil;
import dev.faceless.swiftlib.test.Temporary;
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
            loadConfigsFromFolder(dataFolder, new HashSet<>());
        }
    }

    public void register(String path, Config config) {
        configs.put(path, config);
    }

    public void createConfig(String path) {
        new Config(path);
    }

    private void loadConfigsFromFolder(File folder, Set<File> loadedFiles) {
        File[] files = folder.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                loadConfigsFromFolder(file, loadedFiles);
            }else if (file.getName().endsWith(".yml") && !loadedFiles.contains(file)) {
                String relativePath = file.getPath().replace("plugins" + File.separator + SwiftLib.getPlugin().getName() + File.separator, "");

                if (isPathBlacklisted(relativePath)) continue;
                createConfig(relativePath);
                loadedFiles.add(file);

                if(SwiftLib.isDebugMode()) TextUtil.logInfo("Loaded config: (" + file.getName() + ") from (" + relativePath + ")");
            }
        }
    }

    private boolean isPathBlacklisted(String path) {
        for (String blacklistedPath : blacklistedPaths) {
            if (path.startsWith(blacklistedPath)) return true;
        }
        return false;
    }

    public void blacklistPath(String path) {
        blacklistedPaths.add(path);
    }

    public void blacklistPaths(String... paths) {
        Arrays.stream(paths).forEach(this::blacklistPath);
    }

    public void removeBlacklistedPath(String path) {
        blacklistedPaths.remove(path);
    }

    public Config createOrGetConfig(String path){
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        }
        if(getConfig(path) != null) return getConfig(path);

        return new Config(path);
    }

    @Nullable
    public Config getConfig(String path) {
        path = path.replace("/", File.separator);
        path = path.replace("\\", File.separator);

        Config config;
        config = configs.get(path);
        if(config == null) path += ".yml";
        config = configs.get(path);
        if(config == null) if(SwiftLib.isDebugMode()) TextUtil.logWarning("Attempted to get config from path (" + path + ") but it was null.");
        return config;
    }

    public void reload(String path) {
        Config config = getConfig(path);
        if (config != null) {
            config.reload();
        }
    }

    @Temporary
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

    public List<Config> getConfigs() {
        return new ArrayList<>(configs.values());
    }
}
