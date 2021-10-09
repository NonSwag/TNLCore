package net.nonswag.tnl.core.api.object;

import javax.annotation.Nonnull;

public record Generic<T>(@Nonnull T parameter) implements Duplicable {

    @Nonnull
    public Class<T> getParameterType() {
        return (Class<T>) parameter().getClass();
    }

    @Nonnull
    @Override
    public Generic<T> duplicate() {
        return new Generic<>(parameter());
    }
}
