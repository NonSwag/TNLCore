package net.nonswag.tnl.core.api.event;

import net.nonswag.tnl.core.api.extension.Extension;
import net.nonswag.tnl.core.api.logger.Logger;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class EventManager {

    @Nonnull
    private static final HashMap<String, EventManager> managers = new HashMap<>();

    @Nonnull
    private final Extension extension;
    @Nonnull
    private final HashMap<Class<?>, List<Method>> events = new HashMap<>();

    private EventManager(@Nonnull Extension extension) {
        this.extension = extension;
    }

    @Nonnull
    public Extension getExtension() {
        return extension;
    }

    @Nonnull
    private HashMap<Class<?>, List<Method>> getEvents() {
        return events;
    }

    public void registerEvents(@Nonnull Object listener) {
        Class<?> clazz = listener.getClass();
        List<Method> methods = new ArrayList<>();
        List<Method> events = new ArrayList<>();
        methods.addAll(Arrays.asList(clazz.getMethods()));
        methods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
        for (Method method : methods) {
            if (method.canAccess(this) && method.isAnnotationPresent(Subscribe.class)) events.add(method);
        }
        getEvents().put(clazz, events);
        Logger.debug.println("Registered listener <'" + clazz.getName() + "'>");
    }

    public boolean unregisterListener(@Nonnull Class<?> clazz) {
        if (getEvents().containsKey(clazz)) {
            getEvents().remove(clazz);
            Logger.debug.println("Unregistered listener <'" + clazz.getName() + "'>");
            return true;
        }
        return false;
    }

    @Nonnull
    private static HashMap<String, EventManager> getManagers() {
        return managers;
    }

    @Nonnull
    public static EventManager cast(@Nonnull Extension extension) {
        if (!getManagers().containsKey(extension.getName())) {
            getManagers().put(extension.getName(), new EventManager(extension));
        }
        return getManagers().get(extension.getName());
    }

    public static void callEvent(@Nonnull Event event) {
        for (EventManager manager : getManagers().values()) {
            if (manager.getEvents().containsKey(event.getClass())) {
                List<Method> methods = manager.getEvents().get(event.getClass());
                for (Method method : methods) {
                    Class<?>[] parameters = method.getParameterTypes();
                    if (parameters.length == 1) {
                        if (parameters[0].equals(event.getClass())) {
                            try {
                                method.invoke(event, event);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
}
