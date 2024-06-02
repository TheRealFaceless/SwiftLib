package dev.faceless.swiftlib.lib.storage.yaml;

import dev.faceless.swiftlib.SwiftLib;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Set;

@SuppressWarnings({"ResultOfMethodCallIgnored", "unused"})
public class Config {

    private final String path;
    private FileConfiguration config;
    private File configFile;
    private final File dataFolder;

    /**
     *
     * @param path The path relative to the plugin's data folder where the config file should be located.
     * If the path doesn't exist, it will be created.
     */
    public Config(String path) {
        this.path = path.endsWith(".yml") ? path : path + ".yml";
        this.dataFolder = SwiftLib.getPluginDataFolder();
        try {
            init();
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("Creation of storage file at path " + path + " failed!!");
            throw new RuntimeException(e);
        }
        ConfigManager.getManager().register(path, this);
    }

    public void init() throws IOException {
        File configFile = new File(dataFolder, path);

        if (!configFile.getParentFile().exists()) configFile.getParentFile().mkdirs();
        if (!configFile.exists()) configFile.createNewFile();

        this.configFile = configFile;
        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void save() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("Failed to save config at path: " + path);
            throw new RuntimeException(e);
        }
    }

    public void reload() {
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getConsoleSender().sendMessage("Failed to reload config at path: " + path);
            throw new RuntimeException(e);
        }
    }

    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return config.getConfigurationSection(path);
    }

    public <T> T get(String path, Class<T> type) {
        return type.cast(config.get(path));
    }

    public Set<String> getKeys(boolean deep) {
        return config.getKeys(deep);
    }

    public boolean contains(String path) {
        return config.contains(path);
    }

    public File getConfigFile() {
        return configFile;
    }

    public FileConfiguration getConfig() {
        return config;
    }


}
