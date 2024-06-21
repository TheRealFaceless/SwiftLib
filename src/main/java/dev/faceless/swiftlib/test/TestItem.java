package dev.faceless.swiftlib.test;

import dev.faceless.swiftlib.lib.item.AbstractItem;
import dev.faceless.swiftlib.lib.text.TextContext;
import dev.faceless.swiftlib.lib.util.ItemCreator;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.Objects;

@Temporary
public class TestItem extends AbstractItem {

    public TestItem() {
        super(ItemCreator.get(Material.BOW).setName(TextContext.get().addColored("gray", "Test Item").build(false)).build(), "test-item", true);
        setLeftClickBlock((event, item) -> event.getPlayer().sendMessage("left click block"));
        setLeftClickAir((event, item) -> event.getPlayer().sendMessage("left click air"));
        setRightClickAir((event, item) -> event.getPlayer().sendMessage("right click air"));
        setRightClickBlock((event, item) -> event.getPlayer().sendMessage("right click block"));

        setDeathByItem((event, item) -> event.getEntity().sendMessage("killed entity"));
        setDrop((event, item) -> event.getPlayer().sendMessage("drop"));
        setAttack((event, item) -> event.getDamager().sendMessage("attack"));
        setABreak((event, item) -> event.getPlayer().sendMessage("break"));
        setPhysical((event, item) -> event.getPlayer().sendMessage("physical"));
        setInteractEntity((event, item) -> event.getPlayer().sendMessage("interact at entity"));

        setHeld((event, item) -> event.getPlayer().sendMessage("held"));
        setUnheld((event, item) -> event.getPlayer().sendMessage("unheld"));
        setProjectileLaunch((event, item) -> event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.ITEM_FIRECHARGE_USE, 1, 1));
        setProjectileHitEntity((event, item) -> Objects.requireNonNull(event.getHitEntity()).getWorld().createExplosion(event.getHitEntity().getLocation(), 2));
        setProjectileHitBlock((event, item) -> Objects.requireNonNull(event.getHitBlock()).getWorld().createExplosion(event.getHitBlock().getLocation(), 2));
        setSwapHand((event, item) -> event.getPlayer().sendMessage("swap hand"));
    }
}
