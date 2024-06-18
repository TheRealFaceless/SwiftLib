package dev.faceless.swiftlib.lib.item;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

//TODO: onHeld and onUnheld
public interface Use {
    interface LeftClickAir {
        void onLeftClickAir(PlayerInteractEvent event, AbstractItem item);
    }

    interface LeftClickBlock {
        void onLeftClickBlock(PlayerInteractEvent event, AbstractItem item);
    }

    interface RightClickAir {
        void onRightClickAir(PlayerInteractEvent event, AbstractItem item);
    }

    interface RightClickBlock {
        void onRightClickBlock(PlayerInteractEvent event, AbstractItem item);
    }

    interface Physical {
        void onPhysical(PlayerInteractEvent event, AbstractItem item);
    }

    interface Drop {
        void onDrop(PlayerDropItemEvent event, AbstractItem item);
    }

    interface Attack {
        void onAttack(EntityDamageByEntityEvent event, AbstractItem item);
    }

    interface PlayerInteractEntity {
        void onInteract(PlayerInteractEntityEvent event, AbstractItem item);
    }

    interface DeathByItem {
        void onDeath(EntityDeathEvent event, AbstractItem item);
    }

    interface Place {
        void onPlace(BlockPlaceEvent event, AbstractItem item);
    }

    interface Break {
        void onBreak(BlockBreakEvent event, AbstractItem item);
    }

    interface ProjectileLaunch {
        void onLaunch(ProjectileLaunchEvent event, AbstractItem item);
    }

    interface ProjectileHitBlock {
        void onHitBlock(ProjectileHitEvent event, AbstractItem item);
    }

    interface ProjectileHitEntity {
        void onHitEntity(ProjectileHitEvent event, AbstractItem item);
    }

    interface Held {
        void onHeld(PlayerItemHeldEvent event, AbstractItem item);
    }

    interface Unheld {
        void onUnheld(PlayerItemHeldEvent event, AbstractItem item);
    }

    interface SwapHand {
        void onSwap(PlayerSwapHandItemsEvent event, AbstractItem item);
    }

    interface InventoryClick {
        void onInvClick(InventoryClickEvent event, AbstractItem item);
    }
}
