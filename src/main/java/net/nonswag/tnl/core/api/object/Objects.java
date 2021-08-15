package net.nonswag.tnl.core.api.object;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Objects<V> implements Duplicable {

    @Nullable
    private V value;

    public Objects(@Nullable V value) {
        this.value = value;
    }

    public Objects() {
        this(null);
    }

    @Nullable
    public V getValue() {
        return value;
    }

    public void setValue(@Nullable V value) {
        this.value = value;
    }

    @Nonnull
    public V getOrDefault(@Nonnull V defaultValue) {
        return getValue() == null ? defaultValue : getValue();
    }

    @Nonnull
    public V nonnull(@Nullable String message) {
        if (hasValue()) {
            assert getValue() != null;
            return getValue();
        }
        throw new NullPointerException(message);
    }

    @Nonnull
    public V nonnull() {
        return nonnull(null);
    }

    public boolean hasValue() {
        return getValue() != null;
    }

    @Nonnull
    public static <V> V getOrDefault(@Nullable V value, @Nonnull V defaultValue) {
        return value == null ? defaultValue : value;
    }

    @Nonnull
    public static <V> V nonnull(@Nullable V value) {
        if (value != null) return value;
        throw new NullPointerException();
    }

    @Nonnull
    @Override
    public Objects<V> duplicate() {
        return new Objects<>(getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Objects<?> objects = (Objects<?>) o;
        return java.util.Objects.equals(value, objects.value);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Objects{" +
                "value=" + value +
                '}';
    }
}
