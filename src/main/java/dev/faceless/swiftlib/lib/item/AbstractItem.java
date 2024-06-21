package dev.faceless.swiftlib.lib.item;

import dev.faceless.swiftlib.SwiftLib;
import dev.faceless.swiftlib.lib.util.PersistentDataContainerStringAdapter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Getter
@Setter
@SuppressWarnings("unused")
public class AbstractItem {
    public static NamespacedKey ABSTRACT_ITEM_KEY = new NamespacedKey(SwiftLib.getPlugin(), "abstract-item-key");

    private Use.Attack attack;
    private Use.Drop drop;
    private Use.LeftClickAir leftClickAir;
    private Use.RightClickAir rightClickAir;
    private Use.LeftClickBlock leftClickBlock;
    private Use.RightClickBlock rightClickBlock;
    private Use.Physical physical;
    private Use.PlayerInteractEntity interactEntity;
    private Use.DeathByItem deathByItem;
    private Use.Place place;
    private Use.Break aBreak;
    private Use.ProjectileLaunch projectileLaunch;
    private Use.ProjectileHitBlock projectileHitBlock;
    private Use.ProjectileHitEntity projectileHitEntity;
    private Use.Held held;
    private Use.Unheld unheld;
    private Use.SwapHand swapHand;
    private Use.InventoryClick inventoryClick;
    private Use.Sneak sneak;

    private final ItemStack item;
    private final String id;

    public AbstractItem(ItemStack item, String id, boolean register) {
        this.id = id;
        this.item = item;
        addCustomData();
        if(register) AbstractItemManager.getManger().register(this);
    }

    public static boolean isAbstractItem(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;

        return meta.getPersistentDataContainer().has(ABSTRACT_ITEM_KEY);
    }

    @Nullable
    public static String getIdFromItemStack(ItemStack item) {
        if(!isAbstractItem(item)) return null;
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        PersistentDataContainerStringAdapter adapter = new PersistentDataContainerStringAdapter(pdc, ABSTRACT_ITEM_KEY);

        return adapter.getString("id");
    }

    private void addCustomData() {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        PersistentDataContainerStringAdapter adapter = new PersistentDataContainerStringAdapter(pdc, ABSTRACT_ITEM_KEY);
        adapter.addString("id", id);
        adapter.build();

        item.setItemMeta(meta);
    }

    protected void consumeItemInHand(@NotNull Player player, @NotNull EquipmentSlot hand) {
        ItemStack newItem = player.getInventory().getItem(Objects.requireNonNull(hand));
        if(!player.getGameMode().equals(GameMode.CREATIVE)) newItem.setAmount(newItem.getAmount() - 1);
        player.getInventory().setItem(Objects.requireNonNull(hand), newItem);
    }

    protected void consumeItemInSlot(@NotNull Player player, int slot) {
        ItemStack newItem = player.getInventory().getItem(slot);
        if(newItem == null) return;
        if(!player.getGameMode().equals(GameMode.CREATIVE)) newItem.setAmount(newItem.getAmount() - 1);
        player.getInventory().setItem(slot, newItem);
    }

    protected void consumeItemOnClick(@NotNull PlayerInteractEvent event) {
        event.setCancelled(true);
        ItemStack newItem = event.getPlayer().getInventory().getItem(Objects.requireNonNull(event.getHand()));
        if(!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) newItem.setAmount(newItem.getAmount() - 1);
        event.getPlayer().getInventory().setItem(Objects.requireNonNull(event.getHand()), newItem);
    }
}
