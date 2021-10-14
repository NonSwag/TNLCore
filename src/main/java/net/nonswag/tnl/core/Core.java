package net.nonswag.tnl.core;

import net.nonswag.tnl.core.api.message.Message;

import javax.annotation.Nonnull;

public class Core {

    static {
        Message.init();
    }

    public static void main(@Nonnull String[] args) {
    }

    public static void main(@Nonnull String arg) {
        main(new String[]{arg});
    }
}
