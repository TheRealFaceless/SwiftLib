package dev.faceless.swiftlib.lib.particle.shapes;

import dev.faceless.swiftlib.SwiftLib;
import dev.faceless.swiftlib.lib.particle.AbstractParticleShape;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class CircleParticleShape extends AbstractParticleShape {

    public CircleParticleShape(Particle particle, int radius, int step, double pitch, double roll) {
        super(particle, radius, step, pitch, roll);
    }

    @Override
    public void displayShape(World world, Location center) {
        double angleX = Math.toRadians(pitch);
        double cosX = Math.cos(angleX);
        double sinX = Math.sin(angleX);

        double angleZ = Math.toRadians(roll);
        double cosZ = Math.cos(angleZ);
        double sinZ = Math.sin(angleZ);

        for (int i = 0; i < 360; i += step) {
            double angle = Math.toRadians(i);

            double rotatedX = radius * Math.cos(angle);
            double rotatedY = 0;
            double rotatedZ = radius * Math.sin(angle);

            double tempY = rotatedY * cosX - rotatedZ * sinX;
            rotatedZ = rotatedY * sinX + rotatedZ * cosX;
            rotatedY = tempY;

            double tempX = rotatedX * cosZ - rotatedY * sinZ;
            rotatedY = rotatedX * sinZ + rotatedY * cosZ;
            rotatedX = tempX;

            double finalX = center.getX() + rotatedX;
            double finalY = center.getY() + rotatedY;
            double finalZ = center.getZ() + rotatedZ;

            world.spawnParticle(particle, finalX, finalY, finalZ, 1, 0, 0, 0, 0);
        }
    }

    @Override
    public void displayShapeRepeatedly(World world, Location center, int duration) {
        new BukkitRunnable() {
            private final long startTime = System.currentTimeMillis();

            @Override
            public void run() {
                long elapsedTime = System.currentTimeMillis() - startTime;
                displayShape(world, center);
                if (elapsedTime >= duration * 1000L) cancel();
            }
        }.runTaskTimer(SwiftLib.getPlugin(), 0, 0);
    }
}
