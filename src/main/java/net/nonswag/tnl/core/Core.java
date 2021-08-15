package net.nonswag.tnl.core;

import net.nonswag.tnl.core.api.message.Message;
import net.nonswag.tnl.core.api.settings.Settings;

public class Core {

    public static void main(String[] args) {
        Settings.init();
        Message.init();
    }
}
