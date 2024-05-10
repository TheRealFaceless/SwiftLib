package dev.faceless.swiftlib.test;

import dev.faceless.swiftlib.SwiftLib;
import dev.faceless.swiftlib.lib.command.Command;
import dev.faceless.swiftlib.lib.command.CommandContext;
import dev.faceless.swiftlib.lib.command.ICommand;
import dev.faceless.swiftlib.lib.storage.Config;
import dev.faceless.swiftlib.lib.storage.ConfigManager;
import dev.faceless.swiftlib.lib.text.TextContext;
import dev.faceless.swiftlib.lib.util.ItemCreator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TestCommand extends Command {
    public TestCommand() {
        super("swiftlib");
    }

    @ICommand
    public void say(CommandContext context) {
        CommandSender sender = context.sender();
        String message = context.hasArgs() ? String.join("-", context.args()) : "";
        sender.sendMessage(message);
    }

    @ICommand
    public void test(CommandContext context) {
        CommandSender sender = context.sender();
        Component msg = new TextContext()
                .addColored("#FF5555", "hello")
                .addColored("#FF5500", "world")
                .addClick(TextContext.CLICK_RUN_COMMAND, "/ascend 5",
                        new TextContext().addHover(TextContext.HOVER_SHOW_TEXT, "ascend 5 blocks",
                                new TextContext().addGradient("|||||||||", List.of("#FF0000", "#00FF00")).buildAsString()
                        ).buildAsString(), TextDecoration.UNDERLINED, TextDecoration.BOLD
                ).build();
        sender.sendMessage(msg);
    }

    @ICommand
    public void createconfig(CommandContext context) {
        Player player = context.getSenderAsPlayer();
        if(player == null) return;
        new Config("players/data/" + player.getName(), SwiftLib.plugin);
    }

    @ICommand
    public void listConfigs(CommandContext context) {
        Player player = context.getSenderAsPlayer();
        if(player == null) return;
        ConfigManager.getManager().sendNames();
    }

    @ICommand
    public void menu(CommandContext context) {
        String[] args = context.args();
        Player player = context.getSenderAsPlayer();
        if(player == null) return;
        player.getInventory().addItem(ItemCreator.createNameless(Material.WOODEN_AXE));
    }
}
