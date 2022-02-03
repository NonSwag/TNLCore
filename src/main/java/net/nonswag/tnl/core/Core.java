package net.nonswag.tnl.core;

import net.nonswag.tnl.core.api.file.helper.FileHelper;
import net.nonswag.tnl.core.api.message.Message;

import javax.annotation.Nonnull;

public class Core {

    public Core() {
    }

    public static void main(@Nonnull String[] args) {
        FileHelper.copyResourceFile(Core.class, "messages/system.locale", "Core/Messages/", false);
        FileHelper.copyResourceFile(Core.class, "messages/american-english.locale", "Core/Messages/", false);
        FileHelper.copyResourceFile(Core.class, "messages/german.locale", "Core/Messages/", false);
        Message.init();
    }

    public static void main(@Nonnull String arg) {
        main(new String[]{arg});
    }
}
