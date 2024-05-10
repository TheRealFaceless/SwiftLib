package dev.faceless.swiftlib.lib.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ItemCreator {
    private ItemStack item;
    private ItemMeta meta;

    private ItemCreator(){}

    public static ItemStack create(Component name, Material material) {
        return get(material).setName(name).build();
    }
    public static ItemStack create(Material material) {
        return get(material).build();
    }
    public static ItemStack createNameless(Material material) {
        return get(material).setName(Component.text("")).build();
    }

    public static ItemCreator get(Material material) {
        ItemCreator creator = new ItemCreator();
        creator.item = new ItemStack(material);
        creator.meta = creator.item.getItemMeta();
        return creator;
    }

    public static ItemCreator get(ItemStack item) {
        ItemCreator creator = new ItemCreator();
        creator.item = item.clone();
        creator.meta = item.getItemMeta();
        return creator;
    }

    public ItemCreator setName(Component name) {
        meta.displayName(name);
        return this;
    }
    public ItemCreator setLore(List<Component> lore) {
        meta.lore(lore);
        return this;
    }
    public ItemCreator addEnchantment(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }
    public ItemCreator addItemFlags(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }
    public ItemCreator setUnbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return this;
    }
    public ItemCreator setCustomModelData(int data) {
        meta.setCustomModelData(data);
        return this;
    }
    public <T> ItemCreator setPDC(JavaPlugin plugin, String key, PersistentDataType<T, T> type, T value) {
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
        meta.getPersistentDataContainer().set(namespacedKey, type, value);
        return this;
    }
    public <T> T getPDC(JavaPlugin plugin, String key, PersistentDataType<T, T> type) {
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
        return meta.getPersistentDataContainer().get(namespacedKey, type);
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
}
