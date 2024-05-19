package dev.faceless.swiftlib.lib.util;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

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

    public static RayCastResult rayCast(LivingEntity livingEntity, double range, double stepSize, double entitySearchRadius) {
        Location startLocation = livingEntity.getEyeLocation();
        Vector direction = startLocation.getDirection();

        Location currentLocation = startLocation.clone();
        for (double i = 0; i < range; i += 0.5) {
            currentLocation.add(direction.clone().multiply(stepSize));

            if (currentLocation.getBlock().getType() != Material.AIR) {
                return new RayCastResult(currentLocation.getBlock(), currentLocation.toVector(), null, currentLocation.distance(startLocation));
            }

            Collection<Entity> nearbyEntities = currentLocation.getNearbyEntities(entitySearchRadius, entitySearchRadius, entitySearchRadius);
            for (Entity entity : nearbyEntities) {
                if(entity.equals(livingEntity)) continue;
                return new RayCastResult(null, currentLocation.toVector(), entity, currentLocation.distance(startLocation));
            }
        }
        return null;
    }

    public record RayCastResult(Block hitBlock, Vector hitPosition, Entity hitEntity, double hitDistance) {
    }
}
