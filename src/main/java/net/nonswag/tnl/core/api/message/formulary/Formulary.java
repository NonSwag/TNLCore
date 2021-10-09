package net.nonswag.tnl.core.api.message.formulary;

import net.nonswag.tnl.core.api.message.Placeholder;

import javax.annotation.Nonnull;

public interface Formulary<V> {
    @Nonnull
    Placeholder check(V value);
}
