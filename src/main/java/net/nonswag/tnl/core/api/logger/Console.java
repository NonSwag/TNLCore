package net.nonswag.tnl.core.api.logger;

import net.nonswag.tnl.core.api.command.CommandSource;

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
