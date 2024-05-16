package dev.faceless.swiftlib.lib.menu;

import dev.faceless.swiftlib.SwiftLib;
import dev.faceless.swiftlib.lib.text.TextContext;
import dev.faceless.swiftlib.lib.text.TextUtil;
import dev.faceless.swiftlib.lib.util.ItemCreator;
import dev.faceless.swiftlib.lib.util.MenuUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public abstract class PaginatedMenu {
    private final Component title;
    private final int pageSize;
    private final List<Inventory> pages;
    private final Map<UUID, Integer> viewers;
    private final int currentPage;

    private static final HashMap<UUID, PaginatedMenu> openMenus = new HashMap<>();

    public PaginatedMenu(int pageSize, Component title) {
        this.pageSize = pageSize;
        this.title = title;
        this.pages = new ArrayList<>();
        this.viewers = new HashMap<>();
        this.pages.add(createNewPage(title));
        this.currentPage = 0;
    }

    private Inventory createNewPage(Component title) {
        return Bukkit.createInventory(null, pageSize, title);
    }

    public void addItem(ItemStack item) {
        Inventory lastPage = pages.get(pages.size() - 1);
        int lastPageLastSlotIndex = lastPage.getSize() - 10;

        if (lastPage.firstEmpty() == -1 || lastPage.firstEmpty() > lastPageLastSlotIndex) {
            lastPage = createNewPage(title);
            pages.add(lastPage);
        }
        lastPage.addItem(item);
    }

    public void remove(ItemStack item) {
        for (Inventory page : pages) {
            if (!page.contains(item)) continue;
            page.remove(item);
            break;
        }
    }

    public Inventory getCurrentPage(Player player) {
        return pages.get(viewers.getOrDefault(player.getUniqueId(), currentPage));
    }

    public void nextPage(Player player) {
        int current = viewers.getOrDefault(player.getUniqueId(), currentPage);
        if (current < pages.size() - 1) {
            viewers.put(player.getUniqueId(), current + 1);
            addBorder(player);
            player.openInventory(getCurrentPage(player));
        }
    }

    public void prevPage(Player player) {
        int current = viewers.getOrDefault(player.getUniqueId(), currentPage);
        if (current > 0) {
            viewers.put(player.getUniqueId(), current - 1);
            addBorder(player);
            player.openInventory(getCurrentPage(player));
        }
    }

    public void openForPlayer(Player player) {
        viewers.put(player.getUniqueId(), 0);
        addBorder(player);
        player.openInventory(getCurrentPage(player));
        openMenus.put(player.getUniqueId(), this);
        onMenuOpen(player);
    }

    public void closeForPlayer(Player player) {
        viewers.remove(player.getUniqueId());
        onMenuClose(player);
        if (viewers.isEmpty()) openMenus.remove(player.getUniqueId(), this);
    }

    private void addBorder(Player player) {
        Inventory inventory = getCurrentPage(player);
        MenuUtil.addBottomLayer(inventory, getFiller());
        inventory.setItem(inventory.getSize() - 6, prevPage());
        inventory.setItem(inventory.getSize() - 5, pageCounter(player));
        inventory.setItem(inventory.getSize() - 4, nextPage());
    }

    public abstract void handleClick(InventoryClickEvent event);

    public abstract void onMenuOpen(Player player);

    public abstract void onMenuClose(Player player);

    public static HashMap<UUID, PaginatedMenu> getOpenMenus() {
        return openMenus;
    }

    public static ItemStack prevPage() {
        return ItemCreator.get(Material.ARROW)
                .setName(TextContext.get().addColored(TextContext.GREEN, "Previous Page").build().decoration(TextDecoration.ITALIC, false))
                .setPDC(SwiftLib.getPlugin(), "prev-page", PersistentDataType.STRING, "").build();
    }

    public static ItemStack nextPage() {
        return ItemCreator.get(Material.ARROW)
                .setName(TextContext.get().addColored(TextContext.GREEN, "Next Page").build().decoration(TextDecoration.ITALIC, false))
                .setPDC(SwiftLib.getPlugin(), "next-page", PersistentDataType.STRING, "").build();
    }

    public static ItemStack getFiller() {
        return ItemCreator.get(ItemCreator.createNameless(Material.GRAY_STAINED_GLASS_PANE))
                .setPDC(SwiftLib.getPlugin(), "filler-glass", PersistentDataType.STRING, "").build();
    }

    public ItemStack pageCounter(Player player) {
        return ItemCreator.get(Material.OAK_SIGN)
                .setName(TextContext.get().addColored(TextContext.YELLOW, "Page: " + getPage(player)).build().decoration(TextDecoration.ITALIC, false))
                .setPDC(SwiftLib.getPlugin(), "page-counter", PersistentDataType.STRING, "").build();
    }

    public int getPage(Player player) {
        return viewers.get(player.getUniqueId());
    }

    protected List<Inventory> getPages() {
        return pages;
    }

    protected Map<UUID, Integer> getViewers() {
        return viewers;
    }
}


