package dev.faceless.swiftlib.lib.util;

import org.bukkit.FluidCollisionMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class RayTraceUtil {

    public static RayTraceResult rayTraceBlocks(Player player, int range) {
        return player.rayTraceBlocks(range);
    }

    public static RayTraceResult rayTraceBlocks(Player player, int range, FluidCollisionMode collisionMode) {
        return player.rayTraceBlocks(range, collisionMode);
    }

    public static RayTraceResult rayTraceEntities(Player player, int range) {
        return player.rayTraceEntities(range);
    }

    public static RayTraceResult rayTraceEntities(Player player, int range, boolean ignoreBlocks) {
        return player.rayTraceEntities(range, ignoreBlocks);
    }

    @Nullable
    public static Entity getTargetEntity(Player player, int range) {
        return player.getTargetEntity(range);
    }

    @Nullable
    public static Entity getTargetEntity(Player player, int range, boolean ignoreBlocks) {
        return player.getTargetEntity(range, ignoreBlocks);
    }

    @Nullable
    public static Block getTargetBlockExact(Player player, int range) {
        return player.getTargetBlockExact(range);
    }
}
