package net.nonswag.tnl.core.api.event;

import net.nonswag.tnl.core.api.player.Player;

import javax.annotation.Nonnull;

public abstract class PlayerEvent extends Event {

    @Nonnull
    private final Player player;

    protected PlayerEvent(@Nonnull Player player) {
        this.player = player;
    }

    @Nonnull
    public Player getPlayer() {
        return player;
    }
}
