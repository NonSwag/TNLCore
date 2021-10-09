package net.nonswag.tnl.core.api.command;

import net.nonswag.tnl.core.api.logger.Console;
import net.nonswag.tnl.core.api.platform.PlatformPlayer;

import javax.annotation.Nonnull;

public interface CommandSource {

    void sendMessage(@Nonnull String... messages);

    boolean hasPermission(@Nonnull String permissions);

    default boolean isConsole() {
        return false;
    }

    default boolean isPlayer() {
        return false;
    }

    @Nonnull
    default Console console() {
        return Console.getInstance();
    }

    @Nonnull
    default PlatformPlayer player() {
        if (isPlayer()) return (PlatformPlayer) this;
        throw new ClassCastException("CommandSource is not a player <'" + getClass().getSimpleName() + "'>");
    }
}
