package dev.faceless.swiftlib.lib.util;

import dev.faceless.swiftlib.SwiftLib;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class ShakeUtils {

    public static void triggerShake(Player player, int durationInSeconds, int intensity, boolean playSound) {
        new BukkitRunnable() {
            int counter = 0;
            final Random random = new Random();

            @Override
            public void run() {
                if (counter++ >= durationInSeconds * 20) {
                    this.cancel();
                    return;
                }
                float yaw = player.getLocation().getYaw() + (random.nextFloat() * intensity - ((float) intensity / 2));
                float pitch = player.getLocation().getPitch() + (random.nextFloat() * intensity - ((float) intensity / 2));

                player.setRotation(yaw, pitch);
                if (playSound) playEarthquakeSound(player, intensity);
            }
        }.runTaskTimer(SwiftLib.getPlugin(), 0, 1);
    }

    private static void playEarthquakeSound(Player player, int intensity) {
        float volume = Math.min(1.0f, intensity / 10.0f);
        float pitch = Math.max(0.5f, 1.5f - (intensity / 20.0f));

        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, volume, pitch);
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, volume / 4, pitch);
        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, volume, pitch);
    }
}
