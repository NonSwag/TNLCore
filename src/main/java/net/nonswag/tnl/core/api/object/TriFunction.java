package net.nonswag.tnl.core.api.object;

import javax.annotation.Nonnull;
import java.util.function.Function;

@FunctionalInterface
public interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);

    @Nonnull
    default <W> TriFunction<T, U, V, W> andThen(@Nonnull Function<? super R, ? extends W> after) {
        return (t, u, v) -> after.apply(apply(t, u, v));
    }
}
