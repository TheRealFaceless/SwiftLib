package dev.faceless.swiftlib.test;

import dev.faceless.swiftlib.lib.command.Command;
import dev.faceless.swiftlib.lib.command.CommandContext;
import dev.faceless.swiftlib.lib.command.ICommand;
import dev.faceless.swiftlib.lib.particle.shapes.CircleParticleShape;
import dev.faceless.swiftlib.lib.text.TextContext;
import dev.faceless.swiftlib.lib.util.ShakeUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Particle;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@Temporary
public class TestCommand extends Command {
    public TestCommand() {
        super("swiftlib");
    }

    @ICommand
    public void test(CommandContext context) {
        CommandSender sender = context.sender();
        Component msg = new TextContext()
                .colored("#FF5555", "hello")
                .colored("#FF5500", "world")
                .click(TextContext.CLICK_RUN_COMMAND, "/up 5",
                        new TextContext().hover(TextContext.HOVER_SHOW_TEXT, "ascend 5 blocks", new TextContext().gradient("|||||||||", List.of("#FF0000", "#00FF00")).buildAsString()).buildAsString(), TextDecoration.UNDERLINED, TextDecoration.BOLD)
                .build();
        sender.sendMessage(msg);
    }

    @ICommand
    public void menu(CommandContext context) {
        Player player = context.getSenderAsPlayer();
        if(player == null) return;
        TestMenu.getMenu().openForPlayer(player);
    }

    @ICommand
    public void circle(CommandContext context) {
        String[] args = context.args();
        Player player = context.getSenderAsPlayer();
        if (player == null) return;
        if (args.length != 5) {
            player.sendMessage("Usage: /circle <duration> <radius> <rotationAngleX> <rotationAngleZ>");
            return;
        }

        int duration;
        int radius;
        double rotationAngleX;
        double rotationAngleZ;
        try {
            duration = Integer.parseInt(args[1]);
            radius = Integer.parseInt(args[2]);
            rotationAngleX = Double.parseDouble(args[3]);
            rotationAngleZ = Double.parseDouble(args[4]);
        } catch (NumberFormatException e) {
            player.sendMessage("Invalid arguments! Ensure duration, radius, rotation angles are numbers.");
            return;
        }
        CircleParticleShape shape = new CircleParticleShape(Particle.GLOW, radius, 1, rotationAngleX, rotationAngleZ);
        shape.displayShapeRepeatedly(player.getWorld(), player.getLocation(), duration);
    }

    @ICommand
    public void shake(CommandContext context) {
        String[] args = context.args();
        Player player = context.getSenderAsPlayer();
        if(player == null) return;
        if(args.length != 3) return;
        int duration = 0;
        int intensity = 0;
        try {
            duration = Integer.parseInt(args[1]);
            intensity = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            player.sendMessage("invalid args!!");
        }
        ShakeUtils.triggerShake(player, duration, intensity, false);
    }
}
