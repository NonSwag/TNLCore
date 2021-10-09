package net.nonswag.tnl.core.api.command;

import javax.annotation.Nonnull;

public record Invocation(@Nonnull CommandSource source, @Nonnull String label, @Nonnull String[] arguments) {
}
