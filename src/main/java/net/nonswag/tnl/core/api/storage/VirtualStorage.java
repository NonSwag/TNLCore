package net.nonswag.tnl.core.api.storage;

import net.nonswag.tnl.core.api.object.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class VirtualStorage {

    @Nonnull
    private final HashMap<String, Object> storage = new HashMap<>();

    @Nonnull
    private synchronized HashMap<String, Object> getStorage() {
        return storage;
    }

    @Nonnull
    public Objects<?> get(@Nonnull String key) {
        return new Objects<>(getStorage().get(key));
    }

    @Nonnull
    public <T> Objects<T> get(@Nonnull String key, @Nonnull Class<? extends T> clazz) {
        Object o = getStorage().get(key);
        if (clazz.isInstance(o)) return new Objects<>((T) o);
        return new Objects<>();
    }

    @Nonnull
    public Objects<Integer> getInteger(@Nonnull String key) {
        return get(key, Integer.class);
    }

    @Nonnull
    public Objects<Double> getDouble(@Nonnull String key) {
        return get(key, Double.class);
    }

    @Nonnull
    public Objects<Float> getFloat(@Nonnull String key) {
        return get(key, Float.class);
    }

    @Nonnull
    public Objects<Long> getLong(@Nonnull String key) {
        return get(key, Long.class);
    }

    @Nonnull
    public <T> VirtualStorage put(@Nonnull String key, @Nullable T value) {
        getStorage().put(key, value);
        return this;
    }

    public void removeAfter(@Nonnull String key, long millis) {
        new Thread(() -> {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException ignored) {
            } finally {
                remove(key);
            }
        }).start();
    }

    @Nonnull
    public <T> Objects<T> getOrDefault(@Nonnull String key, @Nullable T object, Class<? extends T> clazz) {
        if (object != null && !clazz.isInstance(getStorage().get(key))) getStorage().put(key, object);
        return get(key, clazz);
    }

    @Nonnull
    public <T> Objects<T> getOrDefault(@Nonnull String key, @Nullable T object) {
        return object != null ? (Objects<T>) getOrDefault(key, object, object.getClass()) : new Objects<>();
    }

    @Nonnull
    public <T> Objects<List<T>> getList(@Nonnull String key, @Nonnull Class<? extends T> clazz) {
        return (Objects<List<T>>) get(key);
    }

    @Nonnull
    public Objects<String> getString(@Nonnull String key) {
        return get(key, String.class);
    }

    public boolean remove(@Nonnull String key, @Nonnull Object value) {
        return getStorage().remove(key, value);
    }

    public void remove(@Nonnull String key) {
        getStorage().remove(key);
    }

    public boolean containsKey(@Nonnull String key) {
        return getStorage().containsKey(key);
    }

    public boolean containsValue(@Nonnull Object value) {
        return getStorage().containsValue(value);
    }

    public <T> boolean compareInstance(@Nonnull String key, @Nonnull Class<? extends T> clazz) {
        return containsKey(key) && get(key, clazz).hasValue();
    }
}
