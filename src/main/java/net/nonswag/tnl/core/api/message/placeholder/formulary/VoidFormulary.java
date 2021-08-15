package net.nonswag.tnl.core.api.message.placeholder.formulary;

import net.nonswag.tnl.core.api.message.placeholder.Placeholder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class VoidFormulary implements Formulary<Void> {

    @Nonnull
    @Override
    public abstract Placeholder check(@Nullable Void value);

    @Nonnull
    public Placeholder check() {
        return check(null);
    }
}
