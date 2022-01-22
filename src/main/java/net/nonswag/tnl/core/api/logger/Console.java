package net.nonswag.tnl.core.api.logger;

import net.nonswag.tnl.core.api.command.CommandSource;
import net.nonswag.tnl.core.api.message.Message;
import net.nonswag.tnl.core.api.message.Placeholder;
import net.nonswag.tnl.core.api.message.key.Key;
import net.nonswag.tnl.core.api.platform.PlatformPlayer;

import javax.annotation.Nonnull;

public class Console implements CommandSource {

    @Nonnull
    private static final Console instance = new Console();

    protected Console() {
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

    @Nonnull
    public static Console getInstance() {
        return instance;
    }
}
