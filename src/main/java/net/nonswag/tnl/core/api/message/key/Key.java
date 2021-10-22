package net.nonswag.tnl.core.api.message.key;

import javax.annotation.Nonnull;

public abstract class Key {

    @Nonnull
    private final String key;

    protected Key(@Nonnull String key) {
        this.key = key;
    }

    @Nonnull
    public final String getKey() {
        return this.key;
    }

    @Nonnull
    protected abstract Key register();

    protected abstract void unregister();

    @Nonnull
    public abstract String message();
}
