package dev.faceless.swiftlib.lib.item;

import dev.faceless.swiftlib.SwiftLib;
import dev.faceless.swiftlib.lib.util.PersistentDataContainerStringAdapter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
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

    private final ItemStack item;
    private final String id;

    public AbstractItem(ItemStack item, String id) {
        this.id = id;
        this.item = item;
        addCustomData();
        AbstractItemManager.getManger().register(this);
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
}
