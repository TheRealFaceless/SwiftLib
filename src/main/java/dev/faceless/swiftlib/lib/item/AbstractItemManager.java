package dev.faceless.swiftlib.lib.item;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class AbstractItemManager {

    private final HashMap<String, AbstractItem> items = new HashMap<>();
    private static AbstractItemManager manger;

    private AbstractItemManager() {}

    public void register(AbstractItem item) {
        items.put(item.getId(), item);
    }

    public static AbstractItemManager getManger() {
        return manger == null ? manger = new AbstractItemManager() : manger;
    }

    @Nullable
    public AbstractItem getGameItem(String id) {
        if(items.containsKey(id)) return items.get(id);
        return null;
    }
}
