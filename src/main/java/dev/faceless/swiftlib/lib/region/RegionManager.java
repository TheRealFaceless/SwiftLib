package dev.faceless.swiftlib.lib.region;

import dev.faceless.swiftlib.lib.storage.yaml.Config;
import dev.faceless.swiftlib.lib.storage.yaml.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unused")
public class RegionManager {
    private static final HashMap<String, Region3d> region3ds = new HashMap<>();
    private static final HashMap<String, Region2d> region2ds = new HashMap<>();

    public static void register(Region3d region3d) {
        region3ds.put(region3d.getName(), region3d);
        saveRegion3d(region3d);
    }

    public static void register(Region2d region2d) {
        region2ds.put(region2d.getName(), region2d);
        saveRegion2d(region2d);
    }

    public static Region3d get3dRegion(String name) {
        return region3ds.get(name);
    }

    public static Region3d get3dRegion(Location location) {
        for(Region3d region3d : region3ds.values()) {
            if (region3d.contains(location)) return region3d;
        }
        return null;
    }

    public static Region2d get2dRegion(String name) {
        return region2ds.get(name);
    }

    public static Region2d get2dRegion(Location location) {
        for(Region2d region2d : region2ds.values()) {
            if (region2d.contains(location)) return region2d;
        }
        return null;
    }

    public static boolean containsRegion3d(Region3d region) {
        return region3ds.values().stream().filter(existingRegion -> !existingRegion.equals(region)).anyMatch(existingRegion -> existingRegion.equals(region));
    }

    public static boolean containsRegion2d(Region2d region) {
        return region2ds.values().stream().filter(existingRegion -> !existingRegion.equals(region)).anyMatch(existingRegion -> existingRegion.equals(region));
    }

    public static boolean containsRegion3d(String name) {
        return region3ds.containsKey(name);
    }

    public static boolean containsRegion2d(String name) {
        return region2ds.containsKey(name);
    }

    public static boolean overlapsWithExisting(Region3d region3d) {
        return region3ds.values().stream().filter(existingRegion -> !existingRegion.equals(region3d)).anyMatch(existingRegion -> existingRegion.overlaps(region3d));
    }

    public static boolean overlapsWithExisting(Region2d region2d) {
        return region2ds.values().stream().filter(existingRegion -> !existingRegion.equals(region2d)).anyMatch(existingRegion -> existingRegion.overlaps(region2d));
    }

    public static void deleteRegion(String name) {
        deleteRegion3d(name);
        deleteRegion2d(name);
    }

    public static void deleteRegion3d(String name) {
        if (region3ds.containsKey(name)) {
            region3ds.remove(name);
            Config configuration = ConfigManager.getManager().createOrGetConfig("internals/region/3dRegions.yml");
            configuration.getConfig().set("regions." + name, null);
            configuration.save();
        }
    }
    public static void deleteRegion2d(String name) {
        if (region2ds.containsKey(name)) {
            region2ds.remove(name);
            Config configuration = ConfigManager.getManager().createOrGetConfig("internals/region/2dRegions.yml");
            configuration.getConfig().set("regions." + name, null);
            configuration.save();
        }
    }

    public static List<Region3d> get3dRegions() {
        return new ArrayList<>(region3ds.values());
    }

    public static List<Region2d> get2dRegions() {
        return new ArrayList<>(region2ds.values());
    }

    private static void saveRegion3d(Region3d region3d) {
        Config configuration = ConfigManager.getManager().createOrGetConfig("internals/region/3dRegions.yml");
        FileConfiguration config = configuration.getConfig();
        String path = "regions." + region3d.getName();

        config.set(path + ".world", region3d.getWorld().getName());

        config.set(path + ".maxX", region3d.getMaxX());
        config.set(path + ".maxY", region3d.getMaxY());
        config.set(path + ".maxZ", region3d.getMaxZ());

        config.set(path + ".minX", region3d.getMinX());
        config.set(path + ".minY", region3d.getMinY());
        config.set(path + ".minZ", region3d.getMinZ());
        configuration.save();
    }
    private static void saveRegion2d(Region2d region2d) {
        Config configuration = ConfigManager.getManager().createOrGetConfig("internals/region/2dRegions.yml");
        FileConfiguration config = configuration.getConfig();
        String path = "regions." + region2d.getName();

        config.set(path + ".world", region2d.getWorld().getName());

        config.set(path + ".maxX", region2d.getMaxX());
        config.set(path + ".maxZ", region2d.getMaxZ());

        config.set(path + ".minX", region2d.getMinX());
        config.set(path + ".minZ", region2d.getMinZ());
        configuration.save();

    }

    private static void loadRegion3d(String name) {
        Config configuration = ConfigManager.getManager().createOrGetConfig("internals/region/3dRegions.yml");
        FileConfiguration config = configuration.getConfig();
        String path = "regions." + name;

        if (config.contains(path)) {
            String worldName = config.getString(path + ".world");
            if(worldName == null) return;
            World world = Bukkit.getWorld(worldName);
            int maxX = config.getInt(path + ".maxX");
            int maxY = config.getInt(path + ".maxY");
            int maxZ = config.getInt(path + ".maxZ");
            int minX = config.getInt(path + ".minX");
            int minY = config.getInt(path + ".minY");
            int minZ = config.getInt(path + ".minZ");

            new Region3d(name, world, minX, minY, minZ, maxX, maxY, maxZ);
        }
    }
    private static void loadRegion2d(String name) {
        Config configuration = ConfigManager.getManager().createOrGetConfig("internals/region/2dRegions.yml");
        FileConfiguration config = configuration.getConfig();
        String path = "regions." + name;

        if (config.contains(path)) {
            String worldName = config.getString(path + ".world");
            if(worldName == null) return;
            World world = Bukkit.getWorld(worldName);
            int maxX = config.getInt(path + ".maxX");
            int maxZ = config.getInt(path + ".maxZ");
            int minX = config.getInt(path + ".minX");
            int minZ = config.getInt(path + ".minZ");

            new Region2d(name, world, minX, minZ, maxX, maxZ);
        }
    }

    public static void saveAllRegion3d() {
        for (Region3d region3d : region3ds.values()) saveRegion3d(region3d);
    }
    public static void saveAllRegion2d() {
        for (Region2d region2d : region2ds.values()) saveRegion2d(region2d);
    }

    public static void loadAllRegion3d() {
        Config configuration = ConfigManager.getManager().createOrGetConfig("internals/region/3dRegions.yml");
        FileConfiguration config = configuration.getConfig();

        if (!config.contains("regions")) return;
        ConfigurationSection regionSection = config.getConfigurationSection("regions");
        if(regionSection == null) return;

        for (String name : regionSection.getKeys(false)) {
            loadRegion3d(name);
        }
    }
    public static void loadAllRegion2d() {
        Config configuration = ConfigManager.getManager().createOrGetConfig("internals/region/2dRegions.yml");
        FileConfiguration config = configuration.getConfig();

        if (!config.contains("regions")) return;
        ConfigurationSection regionSection = config.getConfigurationSection("regions");
        if(regionSection == null) return;

        for (String name : regionSection.getKeys(false)) {
            loadRegion2d(name);
        }
    }

    public static void loadAll() {
        loadAllRegion3d();
        loadAllRegion2d();
    }
    public static void saveAll() {
        saveAllRegion3d();
        saveAllRegion2d();
    }
}