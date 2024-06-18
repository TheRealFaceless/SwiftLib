package dev.faceless.swiftlib.lib.particle;

import lombok.Data;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

@Data
public abstract class AbstractParticleShape {
    protected final Particle particle;
    protected final int radius;
    protected final int step;
    protected final double pitch;
    protected final double roll;

    public abstract void displayShape(World world, Location center);
    public abstract void displayShapeRepeatedly(World world, Location center, int duration);
}

