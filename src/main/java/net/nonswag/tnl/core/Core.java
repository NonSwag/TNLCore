package net.nonswag.tnl.core;

import net.nonswag.tnl.core.api.message.Message;

import javax.annotation.Nonnull;

public final class Core {

    static {
        Message.init();
    }

    private Core() {
    }

    public static void main(@Nonnull String[] args) {
    }

    public static void main(@Nonnull String arg) {
        main(new String[]{arg});
    }
}
