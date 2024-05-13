package dev.faceless.swiftlib.lib.events;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.faceless.swiftlib.SwiftLib;
import dev.faceless.swiftlib.lib.text.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

@CanIgnoreReturnValue
public class GlobalEventHandler {
    private static GlobalEventHandler eventHandler;

    private GlobalEventHandler() {}

    public static GlobalEventHandler get() {
        return eventHandler == null ? eventHandler = new GlobalEventHandler() : eventHandler;
    }

    public <T extends Event> GlobalEventHandler addListener(Class<T> eventClass, EventPriority priority, Consumer<T> consumer) {
        Listener listener = new Listener() {
            @EventHandler public void onEvent(Event event) {
                if (eventClass.isInstance(event)) consumer.accept(eventClass.cast(event));
            }
        };

        Bukkit.getPluginManager().registerEvent(eventClass, listener, priority, (l, event) -> {
            if (eventClass.isInstance(event)) consumer.accept(eventClass.cast(event));
        }, SwiftLib.getPlugin());

        if(SwiftLib.isDebugMode()) TextUtil.logInfo("Registered new listener -> Class: " + eventClass.getSimpleName() + ".class");
        return this;
    }

    public <T extends Event> GlobalEventHandler addListener(Class<T> eventClass, Consumer<T> consumer) {
        addListener(eventClass, EventPriority.NORMAL, consumer);
        return this;
    }
}