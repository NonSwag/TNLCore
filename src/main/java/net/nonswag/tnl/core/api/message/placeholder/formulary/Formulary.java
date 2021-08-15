package net.nonswag.tnl.core.api.message.placeholder.formulary;

import net.nonswag.tnl.core.api.message.placeholder.Placeholder;

import javax.annotation.Nonnull;

public interface Formulary<V> {
    @Nonnull
    Placeholder check(V value);
}
