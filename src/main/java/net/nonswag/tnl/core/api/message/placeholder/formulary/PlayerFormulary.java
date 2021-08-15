package net.nonswag.tnl.core.api.message.placeholder.formulary;

import net.nonswag.tnl.core.api.message.placeholder.Placeholder;
import net.nonswag.tnl.core.api.player.Player;

import javax.annotation.Nonnull;

public abstract class PlayerFormulary implements Formulary<Player> {

    @Nonnull
    @Override
    public abstract Placeholder check(@Nonnull Player player);
}
