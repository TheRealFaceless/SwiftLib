package dev.faceless.swiftlib.test;

import dev.faceless.swiftlib.lib.menu.PaginatedMenu;
import dev.faceless.swiftlib.lib.text.TextContext;
import dev.faceless.swiftlib.lib.text.ConsoleLogger;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@Temporary
public class TestMenu extends PaginatedMenu {
    private static TestMenu menu;

    private TestMenu() {
        super(54, TextContext.get().text("Test Menu").build());

        for (int i = 0; i < 100; i++) {
            ItemStack item = new ItemStack(getRandomMaterial());
            addItem(item);
            ConsoleLogger.logInfo("added " + item.getType().name() + "to slot " + i);
        }
    }

    private Material getRandomMaterial() {
        Material[] materials = Material.values();
        int randomIndex = (int) (Math.random() * materials.length);
        return !materials[randomIndex].isItem() ? getRandomMaterial() : materials[randomIndex];
    }

    public static TestMenu getMenu() {
        return menu == null ? menu = new TestMenu() : menu;
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        int initialSize = getPages().size();
        getMenu().deleteEmptyPages();
        int finalSize = getPages().size();

        event.getWhoClicked().sendMessage("Initial Size: " + initialSize);
        event.getWhoClicked().sendMessage("Final Size: " + finalSize);
    }

    @Override
    public void onMenuOpen(Player player) {

    }

    @Override
    public void onMenuClose(Player player) {

    }
}
