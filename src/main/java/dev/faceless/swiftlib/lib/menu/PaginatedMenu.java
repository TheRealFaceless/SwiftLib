package dev.faceless.swiftlib.lib.menu;

import dev.faceless.swiftlib.SwiftLib;
import dev.faceless.swiftlib.lib.text.TextContext;
import dev.faceless.swiftlib.lib.util.ItemCreator;
import dev.faceless.swiftlib.lib.util.MenuUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

@SuppressWarnings("unused")
public abstract class PaginatedMenu {
    private final Component title;
    private final int pageSize;
    private final List<Inventory> pages;
    private final Map<UUID, Integer> viewers;
    private static final Map<UUID, PaginatedMenu> openMenus = new HashMap<>();

    public PaginatedMenu(int pageSize, Component title) {
        this.pageSize = pageSize;
        this.title = title;
        this.pages = new ArrayList<>();
        this.viewers = new HashMap<>();
        this.pages.add(createNewPage());
    }

    private Inventory createNewPage() {
        return Bukkit.createInventory(null, pageSize, title);
    }

    public void addItem(ItemStack item) {
        Inventory lastPage = pages.get(pages.size() - 1);
        int lastPageLastSlotIndex = lastPage.getSize() - 10;

        if (lastPage.firstEmpty() == -1 || lastPage.firstEmpty() > lastPageLastSlotIndex) {
            lastPage = createNewPage();
            pages.add(lastPage);
        }
        lastPage.addItem(item);
    }

    public void addItemToSlot(int pageIndex, int slot, ItemStack item) {
        if (pageIndex >= pages.size() || pageIndex < 0) {
            throw new IndexOutOfBoundsException("Page index out of bounds");
        }
        Inventory page = pages.get(pageIndex);
        page.setItem(slot, item);
    }

    public void remove(ItemStack item) {
        Iterator<Inventory> iterator = pages.iterator();
        while (iterator.hasNext()) {
            Inventory page = iterator.next();
            if (page.contains(item)) {
                page.remove(item);
                if (isPageEmpty(page) && pages.size() > 1) iterator.remove();
                return;
            }
        }
    }

    private boolean isPageEmpty(Inventory page) {
        for (ItemStack item : page.getContents()) {
            if (item == null || isPageItem(item) || !item.getType().isItem()) continue;
            return false;
        }
        return true;
    }

    private boolean isPageItem(ItemStack item) {
        return item.equals(nextPage()) ||
                item.equals(prevPage()) ||
                item.equals(getFiller()) ||
                isPageCounter(item);
    }

    public void deleteEmptyPages() {
        Iterator<Inventory> iterator = pages.iterator();
        while (iterator.hasNext()) {
            Inventory page = iterator.next();
            if (pages.indexOf(page) == 0) continue;

            if (!isPageEmpty(page)) continue;
            for (UUID playerId : getViewers().keySet()) {
                Player player = Bukkit.getPlayer(playerId);
                if (player != null && player.getOpenInventory().getTopInventory().equals(page)) prevPage(player);
            }
            iterator.remove();
        }
    }

    public Inventory getCurrentPage(Player player) {
        int currentPageIndex = viewers.getOrDefault(player.getUniqueId(), 0);
        if (pages.isEmpty()) throw new IllegalStateException("Pages is empty");

        return pages.get(Math.min(currentPageIndex, pages.size() - 1));
    }

    public void nextPage(Player player) {
        int currentPage = viewers.getOrDefault(player.getUniqueId(), 0);
        if (currentPage < pages.size() - 1) {
            viewers.put(player.getUniqueId(), currentPage + 1);
            openPageForPlayer(player);
        }
    }

    public void prevPage(Player player) {
        int currentPage = viewers.getOrDefault(player.getUniqueId(), 0);
        if (currentPage > 0) {
            viewers.put(player.getUniqueId(), currentPage - 1);
            openPageForPlayer(player);
        }
    }

    public void openForPlayer(Player player) {
        viewers.put(player.getUniqueId(), 0);
        openPageForPlayer(player);
        openMenus.put(player.getUniqueId(), this);
        if(!viewers.containsKey(player.getUniqueId())) onMenuOpen(player);

    }

    public void closeForPlayer(Player player) {
        viewers.remove(player.getUniqueId());
        if(!viewers.containsKey(player.getUniqueId())) onMenuClose(player);
        openMenus.remove(player.getUniqueId());
    }

    private void openPageForPlayer(Player player) {
        addBorder(player);
        player.openInventory(getCurrentPage(player));
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

    public static Map<UUID, PaginatedMenu> getOpenMenus() {
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
                .setName(TextContext.get().addColored(TextContext.YELLOW, "Page: " + (getPage(player) + 1)).build().decoration(TextDecoration.ITALIC, false))
                .setPDC(SwiftLib.getPlugin(), "page-counter", PersistentDataType.STRING, "").build();
    }

    public boolean isPageCounter(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(SwiftLib.getPlugin(), "page-counter"));
    }

    public int getPage(Player player) {
        return viewers.getOrDefault(player.getUniqueId(), 0);
    }

    protected List<Inventory> getPages() {
        return pages;
    }

    protected Map<UUID, Integer> getViewers() {
        return viewers;
    }
}


