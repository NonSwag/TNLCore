package net.nonswag.tnl.core.api.object;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface TriConsumer<T, U, V> {
    void accept(T t, U u, V v);

    @Nonnull
    default TriConsumer<T, U, V> andThen(@Nonnull TriConsumer<? super T, ? super U, ? super V> after) {
        return (t, u, v) -> {
            this.accept(t, u, v);
            after.accept(t, u, v);
        };
    }
}
