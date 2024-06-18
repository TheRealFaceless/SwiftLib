package dev.faceless.swiftlib.lib.listeners;

import dev.faceless.swiftlib.SwiftLib;
import dev.faceless.swiftlib.lib.events.GlobalEventHandler;
import dev.faceless.swiftlib.lib.item.AbstractItem;
import dev.faceless.swiftlib.lib.item.AbstractItemManager;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class AbstractItemListener {
    public static final NamespacedKey PROJECTILE_KEY = new NamespacedKey(SwiftLib.getPlugin(), "projectile-key");
    private static final GlobalEventHandler eventHandler = GlobalEventHandler.get();

    public static void init() {
        eventHandler.addListener(PlayerDropItemEvent.class, event-> {
            ItemStack item = event.getItemDrop().getItemStack();
            String id = AbstractItem.getIdFromItemStack(item);
            AbstractItem abstractItem = AbstractItemManager.getManger().getGameItem(id);
            if(abstractItem == null || abstractItem.getDrop() == null) return;
            abstractItem.getDrop().onDrop(event, abstractItem);
        });
        eventHandler.addListener(PlayerInteractEvent.class, event-> {
            ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();
            ItemStack offHand = event.getPlayer().getInventory().getItemInOffHand();
            EquipmentSlot equipmentSlot = null;
            AbstractItem abstractItem = null;
            String idOff = AbstractItem.getIdFromItemStack(offHand);
            String idMain = AbstractItem.getIdFromItemStack(mainHand);
            if(idOff != null) {
                abstractItem = AbstractItemManager.getManger().getGameItem(idOff);
                equipmentSlot = EquipmentSlot.OFF_HAND;
            }
            if(idMain != null) {
                abstractItem = AbstractItemManager.getManger().getGameItem(idMain);
                equipmentSlot = EquipmentSlot.HAND;
            }
            if(abstractItem == null) return;
            switch (event.getAction()) {
                case LEFT_CLICK_AIR -> {
                    if(event.getHand() != equipmentSlot) return;
                    if(abstractItem.getLeftClickAir() == null) return;
                    abstractItem.getLeftClickAir().onLeftClickAir(event, abstractItem);
                }
                case LEFT_CLICK_BLOCK -> {
                    if(event.getHand() != equipmentSlot) return;
                    if(abstractItem.getLeftClickBlock() == null) return;
                    abstractItem.getLeftClickBlock().onLeftClickBlock(event, abstractItem);
                }
                case RIGHT_CLICK_AIR -> {
                    if(event.getHand() != equipmentSlot) return;
                    if(abstractItem.getRightClickAir() == null) return;
                    abstractItem.getRightClickAir().onRightClickAir(event, abstractItem);
                }
                case RIGHT_CLICK_BLOCK -> {
                    if(event.getHand() != equipmentSlot) return;
                    if (abstractItem.getRightClickBlock() == null) return;
                    abstractItem.getRightClickBlock().onRightClickBlock(event, abstractItem);
                }
                case PHYSICAL -> {
                    if(event.getHand() != equipmentSlot) return;
                    if (abstractItem.getPhysical() == null) return;
                    abstractItem.getPhysical().onPhysical(event, abstractItem);
                }
            }
        });
        eventHandler.addListener(EntityDamageByEntityEvent.class, event-> {
            if(!(event.getDamager() instanceof LivingEntity damager && event.getEntity() instanceof LivingEntity)) return;
            if(damager.getEquipment() == null) return;
            ItemStack mainHand = damager.getEquipment().getItemInMainHand();
            ItemStack offHand = damager.getEquipment().getItemInOffHand();
            AbstractItem abstractItem = null;
            String idOff = AbstractItem.getIdFromItemStack(offHand);
            String idMain = AbstractItem.getIdFromItemStack(mainHand);
            if(idOff != null) abstractItem = AbstractItemManager.getManger().getGameItem(idOff);
            if(idMain != null) abstractItem = AbstractItemManager.getManger().getGameItem(idMain);
            if(abstractItem == null || abstractItem.getAttack() == null) return;
            abstractItem.getAttack().onAttack(event, abstractItem);
        });
        eventHandler.addListener(PlayerInteractEntityEvent.class, event-> {
            ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();
            ItemStack offHand = event.getPlayer().getInventory().getItemInOffHand();
            EquipmentSlot equipmentSlot = null;
            AbstractItem abstractItem = null;
            String idOff = AbstractItem.getIdFromItemStack(offHand);
            String idMain = AbstractItem.getIdFromItemStack(mainHand);
            if(idOff != null) {
                abstractItem = AbstractItemManager.getManger().getGameItem(idOff);
                equipmentSlot = EquipmentSlot.OFF_HAND;
            }
            if(idMain != null) {
                abstractItem = AbstractItemManager.getManger().getGameItem(idMain);
                equipmentSlot = EquipmentSlot.HAND;
            }
            if(abstractItem == null || event.getHand() != equipmentSlot) return;
            if (abstractItem.getInteractEntity() == null) return;
            abstractItem.getInteractEntity().onInteract(event, abstractItem);
        });
        eventHandler.addListener(EntityDeathEvent.class, event-> {
            LivingEntity entity = event.getEntity();
            LivingEntity killer = entity.getKiller();
            if (killer == null) {
                EntityDamageEvent lastDamageEvent = entity.getLastDamageCause();
                if (!(lastDamageEvent instanceof EntityDamageByEntityEvent damageByEntityEvent)) return;
                Entity damager = damageByEntityEvent.getDamager();
                if (damager instanceof Projectile projectile) {
                    if (!(projectile.getShooter() instanceof LivingEntity)) return;
                    killer = (LivingEntity) projectile.getShooter();
                } else if (damager instanceof LivingEntity) {
                    killer = (LivingEntity) damager;
                }
            }
            if (killer == null || killer.getEquipment() == null) return;
            ItemStack mainHand = killer.getEquipment().getItemInMainHand();
            ItemStack offHand = killer.getEquipment().getItemInOffHand();
            AbstractItem abstractItem = null;
            String idOff = AbstractItem.getIdFromItemStack(offHand);
            String idMain = AbstractItem.getIdFromItemStack(mainHand);
            if(idOff != null) abstractItem = AbstractItemManager.getManger().getGameItem(idOff);
            if(idMain != null) abstractItem = AbstractItemManager.getManger().getGameItem(idMain);
            if (abstractItem == null || abstractItem.getDeathByItem() == null) return;
            abstractItem.getDeathByItem().onDeath(event, abstractItem);
        });
        eventHandler.addListener(BlockPlaceEvent.class, event-> {
            ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();
            ItemStack offHand = event.getPlayer().getInventory().getItemInOffHand();
            AbstractItem abstractItem = null;
            String idOff = AbstractItem.getIdFromItemStack(offHand);
            String idMain = AbstractItem.getIdFromItemStack(mainHand);
            if(idOff != null) abstractItem = AbstractItemManager.getManger().getGameItem(idOff);
            if(idMain != null) abstractItem = AbstractItemManager.getManger().getGameItem(idMain);
            if(abstractItem == null) return;
            if (abstractItem.getPlace() != null) abstractItem.getPlace().onPlace(event, abstractItem);
        });
        eventHandler.addListener(BlockBreakEvent.class, event -> {
            ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
            AbstractItem abstractItem = null;
            String itemId = AbstractItem.getIdFromItemStack(item);
            if(itemId != null) abstractItem = AbstractItemManager.getManger().getGameItem(itemId);
            if(abstractItem == null) return;
            if(abstractItem.getABreak() == null) return;
            abstractItem.getABreak().onBreak(event, abstractItem);
        });
        eventHandler.addListener(ProjectileLaunchEvent.class, event-> {
            if(!(event.getEntity().getShooter() instanceof LivingEntity shooter)) return;
            if(shooter.getEquipment() == null) return;
            ItemStack mainHand = shooter.getEquipment().getItemInMainHand();
            ItemStack offHand = shooter.getEquipment().getItemInOffHand();
            AbstractItem abstractItem = null;
            String idOff = AbstractItem.getIdFromItemStack(offHand);
            String idMain = AbstractItem.getIdFromItemStack(mainHand);
            if(idOff != null) abstractItem = AbstractItemManager.getManger().getGameItem(idOff);
            if(idMain != null) abstractItem = AbstractItemManager.getManger().getGameItem(idMain);
            if(abstractItem == null) return;
            event.getEntity()
                    .getPersistentDataContainer()
                    .set(PROJECTILE_KEY, PersistentDataType.STRING, abstractItem.getId());
            if(abstractItem.getProjectileLaunch() == null) return;
            abstractItem.getProjectileLaunch().onLaunch(event, abstractItem);
        });
        eventHandler.addListener(ProjectileHitEvent.class, event-> {
            if(!(event.getEntity().getShooter() instanceof LivingEntity)) return;
            if (!event.getEntity().getPersistentDataContainer().has(PROJECTILE_KEY)) return;
            String id = event.getEntity().getPersistentDataContainer().get(PROJECTILE_KEY, PersistentDataType.STRING);
            if(id == null) return;
            AbstractItem abstractItem = AbstractItemManager.getManger().getGameItem(id);
            if (abstractItem == null) return;
            if(!event.getEntity().isDead()) event.getEntity().getPersistentDataContainer().remove(PROJECTILE_KEY);
            if(event.getHitEntity() != null) {
                if(abstractItem.getProjectileHitEntity() == null) return;
                abstractItem.getProjectileHitEntity().onHitEntity(event, abstractItem);
            }else if (event.getHitBlock() != null) {
                if (abstractItem.getProjectileHitBlock() == null) return;
                abstractItem.getProjectileHitBlock().onHitBlock(event, abstractItem);
            }
        });
        eventHandler.addListener(PlayerItemHeldEvent.class, event -> {
            ItemStack newItem = event.getPlayer().getInventory().getItem(event.getNewSlot());
            ItemStack previousItem = event.getPlayer().getInventory().getItem(event.getPreviousSlot());

            String idNew = AbstractItem.getIdFromItemStack(newItem);
            String idPrev = AbstractItem.getIdFromItemStack(previousItem);
            if(idNew != null) {
                AbstractItem abstractItem = AbstractItemManager.getManger().getGameItem(idNew);
                if(abstractItem == null) return;
                if(abstractItem.getHeld() != null) abstractItem.getHeld().onHeld(event, abstractItem);
                return;
            }
            if(idPrev != null) {
                AbstractItem abstractItem = AbstractItemManager.getManger().getGameItem(idPrev);
                if(abstractItem == null) return;
                if(abstractItem.getUnheld() != null) abstractItem.getUnheld().onUnheld(event, abstractItem);
            }
        });
        eventHandler.addListener(PlayerSwapHandItemsEvent.class, event -> {
            ItemStack mainHand = event.getMainHandItem();
            ItemStack offHand = event.getOffHandItem();
            AbstractItem abstractItem = null;
            String idOff = AbstractItem.getIdFromItemStack(offHand);
            String idMain = AbstractItem.getIdFromItemStack(mainHand);
            if(idOff != null) abstractItem = AbstractItemManager.getManger().getGameItem(idOff);
            if(idMain != null) abstractItem = AbstractItemManager.getManger().getGameItem(idMain);
            if(abstractItem == null) return;
            if (abstractItem.getSwapHand() != null) abstractItem.getSwapHand().onSwap(event, abstractItem);
        });
        eventHandler.addListener(InventoryClickEvent.class, event -> {
            ItemStack item = event.getCurrentItem();
            String id = AbstractItem.getIdFromItemStack(item);
            AbstractItem abstractItem = AbstractItemManager.getManger().getGameItem(id);
            if(abstractItem == null || abstractItem.getDrop() == null) return;
            abstractItem.getInventoryClick().onInvClick(event, abstractItem);
        });
        eventHandler.addListener(PlayerToggleSneakEvent.class, event-> {
            ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();
            ItemStack offHand = event.getPlayer().getInventory().getItemInOffHand();
            AbstractItem abstractItem = null;
            String idOff = AbstractItem.getIdFromItemStack(offHand);
            String idMain = AbstractItem.getIdFromItemStack(mainHand);
            if(idOff != null) abstractItem = AbstractItemManager.getManger().getGameItem(idOff);
            if(idMain != null) abstractItem = AbstractItemManager.getManger().getGameItem(idMain);
            if(abstractItem == null) return;
            if (abstractItem.getSneak() == null) return;
            abstractItem.getSneak().onSneak(event, abstractItem);
        });
    }
}
