package dev.faceless.swiftlib.test;

import dev.faceless.swiftlib.lib.menu.PaginatedMenu;
import dev.faceless.swiftlib.lib.text.TextContext;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@Temporary
public class TestMenu extends PaginatedMenu {
    private static TestMenu menu;

    private TestMenu() {
        super(36, TextContext.get().add("Test Menu").build());

        for (int i = 0; i < 36; i++) {
            ItemStack item = new ItemStack(getRandomMaterial());
            addItem(item);
            // TextUtil.logInfo("added " + item.getType().name() + "to slot " + i);
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

    }


    @Override
    public void onMenuOpen(Player player) {

    }

    @Override
    public void onMenuClose(Player player) {

    }
}
