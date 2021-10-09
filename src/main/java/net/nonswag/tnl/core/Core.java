package net.nonswag.tnl.core;

import net.nonswag.tnl.core.api.message.Message;

public class Core {

    static {
        Message.init();
    }

    public static void main(String[] args) {
    }

    public interface Injector {
    }
}
