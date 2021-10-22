package net.nonswag.tnl.core.api.logger;

import net.nonswag.tnl.core.api.command.CommandSource;
import net.nonswag.tnl.core.api.message.Message;
import net.nonswag.tnl.core.api.message.Placeholder;
import net.nonswag.tnl.core.api.message.key.Key;
import net.nonswag.tnl.core.api.platform.PlatformPlayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Console implements CommandSource {

    @Nonnull
    private static final Console instance = new Console();

    private Console() {
    }

    @Override
    public void sendMessage(@Nonnull String... messages) {
        for (String message : messages) Logger.info.println(message);
    }

    @Override
    public void sendMessage(@Nonnull Key key, @Nonnull PlatformPlayer player, @Nonnull Placeholder... placeholders) {
        Logger.info.println(Message.format(key.message(), player, placeholders));
    }

    @Override
    public void sendMessage(@Nonnull Key key, @Nonnull Placeholder... placeholders) {
        Logger.info.println(Message.format(key.message(), placeholders));
    }

    @Override
    public boolean hasPermission(@Nonnull String permissions) {
        return true;
    }

    @Override
    public boolean isConsole() {
        return true;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return object instanceof Console;
    }

    @Nonnull
    public static Console getInstance() {
        return instance;
    }
}
