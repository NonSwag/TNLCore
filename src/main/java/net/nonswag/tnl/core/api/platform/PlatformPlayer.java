package net.nonswag.tnl.core.api.platform;

import net.nonswag.tnl.core.api.command.CommandSource;

import javax.annotation.Nonnull;

public interface PlatformPlayer extends CommandSource {

    @Nonnull
    String getName();
}
