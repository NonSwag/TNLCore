package net.nonswag.tnl.core.api.object;

import javax.annotation.Nonnull;

public interface Getter<V> {
    V get();

    @Nonnull
    default String getAsString() {
        V v = get();
        return v == null ? "null" : v.toString();
    }
}
