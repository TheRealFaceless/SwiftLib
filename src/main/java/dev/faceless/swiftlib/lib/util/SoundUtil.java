package dev.faceless.swiftlib.lib.util;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class SoundUtil {

    public static void ui_click(Player player) {
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
    }

    public static void ui_click(Player player, float volume, float pitch) {
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, volume, pitch);
    }
}
