package net.nonswag.tnl.core.api.message.formulary;

import net.nonswag.tnl.core.api.message.Placeholder;
import net.nonswag.tnl.core.api.platform.PlatformPlayer;

import javax.annotation.Nonnull;

public abstract class PlayerFormulary implements Formulary<PlatformPlayer> {

    @Nonnull
    @Override
    public abstract Placeholder check(@Nonnull PlatformPlayer player);
}
