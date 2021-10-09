package net.nonswag.tnl.core.api.object;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class Pair<K, V> implements Duplicable {

    @Nonnull
    private K key;
    @Nullable
    private V value;

    public Pair(@Nonnull K key, @Nullable V value) {
        this.value = value;
        this.key = key;
    }

    @Nonnull
    public K getKey() {
        return key;
    }

    @Nullable
    public V getValue() {
        return value;
    }

    @Nonnull
    public V nonnull(@Nonnull String message) {
        assert getValue() != null : message;
        return getValue();
    }

    @Nonnull
    public V nonnull() {
        return nonnull("there is no value defined for this pair");
    }

    public void setKey(@Nonnull K key) {
        this.key = key;
    }

    public void setValue(@Nullable V value) {
        this.value = value;
    }

    @Nonnull
    @Override
    public Pair<K, V> duplicate() {
        return new Pair<>(getKey(), getValue());
    }

    @Override
    public String toString() {
        return "Pair{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return key.equals(pair.key) && Objects.equals(value, pair.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
