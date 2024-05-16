package dev.faceless.swiftlib.lib.menu;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public abstract class Menu {
    private static final Map<UUID, Menu> openMenus = new HashMap<>();
    private final Map<Integer, MenuClick> menuClickActions = new HashMap<>();
    private MenuClick generalClickAction;
    private MenuClick generalInvClickAction;
    private MenuDrag generalInvDragAction;
    private MenuDrag generalDragAction;
    private MenuOpen openAction;
    private MenuClose closeAction;
    public final UUID uuid;
    private final Inventory inventory;

    public Menu(int size, Component name) {
        uuid = UUID.randomUUID();
        inventory = Bukkit.createInventory(null, size, name);
    }

    public static Menu getMenu(Player p) {
        return openMenus.getOrDefault(p.getUniqueId(), null);
    }

    public UUID getUuid() {
        return uuid;
    }

    public MenuClick getAction(int index) {
        return menuClickActions.getOrDefault(index, null);
    }

    public MenuClick getGeneralClickAction() {
        return generalClickAction;
    }

    protected void setGeneralClickAction(MenuClick generalClickAction) {
        this.generalClickAction = generalClickAction;
    }

    public MenuClick getGeneralInvClickAction() {
        return generalInvClickAction;
    }

    protected void setGeneralInvClickAction(MenuClick generalInvClickAction) {
        this.generalInvClickAction = generalInvClickAction;
    }

    public MenuDrag getGeneralDragAction() {
        return generalDragAction;
    }

    public MenuDrag getGeneralInvDragAction() {
        return generalInvDragAction;
    }

    protected void setGeneralDragAction(MenuDrag generalDragAction) {
        this.generalDragAction = generalDragAction;
    }

    public void setGeneralInvDragAction(MenuDrag generalInvDragAction) {
        this.generalInvDragAction = generalInvDragAction;
    }

    protected void setOpenAction(MenuOpen openAction) {
        this.openAction = openAction;
    }

    protected void setCloseAction(MenuClose closeAction) {
        this.closeAction = closeAction;
    }

    public void setItem(int index, ItemStack item) {
        inventory.setItem(index, item);
    }

    public void setItem(int index, ItemStack item, MenuClick action) {
        inventory.setItem(index, item);
        if (action == null) menuClickActions.remove(index);
        else menuClickActions.put(index, action);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void open(Player p) {
        p.openInventory(inventory);
        openMenus.put(p.getUniqueId(), this);
        if(openAction != null) openAction.open(p);
    }

    public void close(Player p) {
        p.closeInventory();
        openMenus.entrySet().removeIf(entry -> {
            if(!entry.getKey().equals(p.getUniqueId())) return false;
            if(closeAction != null) closeAction.close(p);

            return true;
        });
    }

    public void remove() {
        openMenus.entrySet().removeIf(entry -> {
            if (!entry.getValue().getUuid().equals(uuid)) return false;
            Player p = Bukkit.getPlayer(entry.getKey());
            if (p == null) return false;
            if (closeAction != null) closeAction.close(p);

            return true;
        });
    }

    public interface MenuClick {
        void click(Player p, InventoryClickEvent event);
    }

    public interface MenuDrag {
        void drag(Player p, InventoryDragEvent event);
    }

    public interface MenuOpen {
        void open(Player p);
    }

    public interface MenuClose {
        void close(Player p);
    }
}
