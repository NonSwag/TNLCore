package net.nonswag.tnl.core.api.command;

import net.nonswag.tnl.core.api.logger.Console;
import net.nonswag.tnl.core.api.message.Placeholder;
import net.nonswag.tnl.core.api.message.key.Key;
import net.nonswag.tnl.core.api.platform.PlatformPlayer;

import javax.annotation.Nonnull;

public interface CommandSource {

    void sendMessage(@Nonnull String... messages);

    void sendMessage(@Nonnull Key key, @Nonnull PlatformPlayer player, @Nonnull Placeholder... placeholders);

    void sendMessage(@Nonnull Key key, @Nonnull Placeholder... placeholders);

    boolean hasPermission(@Nonnull String permissions);

    default boolean isConsole() {
        return this instanceof Console;
    }

    default boolean isPlayer() {
        return this instanceof PlatformPlayer;
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
