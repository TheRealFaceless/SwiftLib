package dev.faceless.swiftlib.lib.util;

import dev.faceless.swiftlib.SwiftLib;
import dev.faceless.swiftlib.lib.text.ConsoleLogger;
import net.kyori.adventure.util.TriState;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class WorldManager {

    public static World loadWorld(String name) {
        if (new File(Bukkit.getWorldContainer(), name).exists()) return Builder.of(name).build();
        else Bukkit.getConsoleSender().sendMessage("World '" + name + "' does not exist.");

        return null;
    }

    public static boolean worldExists(String name) {
        return Bukkit.getWorld(name) != null;
    }

    public static List<World> getLoadedWorlds() {
        return new ArrayList<>(Bukkit.getWorlds());
    }

    public static boolean unloadWorld(String name, boolean force) {
        World world = Bukkit.getWorld(name);
        if (world == null) return false;

        if(Bukkit.isTickingWorlds() && !force) return false;
        else if (!Bukkit.isTickingWorlds()) return Bukkit.unloadWorld(world, true);

        return false;
    }

    public static boolean unloadWorld(World world, boolean force) {
        if (world == null) return false;

        if(Bukkit.isTickingWorlds() && !force) return false;
        else if (!Bukkit.isTickingWorlds()) return Bukkit.unloadWorld(world, true);

        return false;
    }

    public static boolean deleteWorld(String name, boolean force) {
        World world = Bukkit.getWorld(name);
        if (world == null || !world.getPlayers().isEmpty()) return false;

        if(!unloadWorld(world, force)) return false;
        File folder = world.getWorldFolder();
        return deleteFolder(folder);
    }

    private static boolean deleteFolder(File folder) {
        if (!folder.isDirectory()) return folder.delete();
        File[] files = folder.listFiles();

        if (files == null) return folder.delete();
        for (File file : files) deleteFolder(file);

        boolean success = folder.delete();
        if(SwiftLib.isDebugMode()) ConsoleLogger.logWarning("Failed to delete world " + folder.getName());
        return success;
    }

    public static void teleportPlayersToWorld(World world, Player... players) {
        if (world == null) return;
        for (Player player : players) player.teleport(world.getSpawnLocation());
    }

    public static class Builder {

        private final WorldCreator creator;

        private Builder(String name) {
            this.creator = new WorldCreator(name);
        }

        public static Builder of(String name) {
            return new Builder(name);
        }

        public Builder biomeProvider(String provider) {
            this.creator.biomeProvider(provider);
            return this;
        }

        public Builder biomeProvider(BiomeProvider provider) {
            this.creator.biomeProvider(provider);
            return this;
        }

        public Builder copy(World world) {
            this.creator.copy(world);
            return this;
        }

        public Builder generator(ChunkGenerator generator) {
            this.creator.generator(generator);
            return this;
        }

        public Builder generator(String generator) {
            this.creator.generator(generator);
            return this;
        }

        public Builder keepSpawnLoaded(TriState state) {
            this.creator.keepSpawnLoaded(state);
            return this;
        }

        public Builder environment(World.Environment environment) {
            this.creator.environment(environment);
            return this;
        }

        public Builder generateStructures(boolean generate) {
            this.creator.generateStructures(generate);
            return this;
        }

        public Builder seed(long seed) {
            this.creator.seed(seed);
            return this;
        }

        public Builder type(WorldType type) {
            this.creator.type(type);
            return this;
        }

        public Builder hardcore(boolean hardcore) {
            this.creator.hardcore(hardcore);
            return this;
        }

        public Builder generatorSettings(String generatorSettings) {
            this.creator.generatorSettings(generatorSettings);
            return this;
        }

        public World build() {
            return creator.createWorld();
        }
    }
}
