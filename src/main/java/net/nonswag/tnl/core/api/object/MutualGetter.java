package net.nonswag.tnl.core.api.object;

import javax.annotation.Nonnull;

public interface MutualGetter<I, O> {
    O get(@Nonnull I input);

    @Nonnull
    default String getAsString(@Nonnull I input) {
        O output = get(input);
        return output == null ? "null" : output.toString();
    }
}
