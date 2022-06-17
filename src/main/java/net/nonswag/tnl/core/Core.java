package net.nonswag.tnl.core;

import lombok.Getter;
import net.nonswag.tnl.core.api.file.helper.FileHelper;
import net.nonswag.tnl.core.api.logger.Logger;
import net.nonswag.tnl.core.api.message.Message;

import javax.annotation.Nonnull;
import java.io.File;

public class Core {

    @Getter
    @Nonnull
    private static final File dataFolder = new File("Core");

    private Core() {
    }

    public static void main(@Nonnull String[] args) {
        try {
            FileHelper.copyResourceFile(Core.class, "messages/system.locale", "Core/Messages/", false);
            FileHelper.copyResourceFile(Core.class, "messages/american-english.locale", "Core/Messages/", false);
            FileHelper.copyResourceFile(Core.class, "messages/german.locale", "Core/Messages/", false);
            Message.init();
            System.setErr(Logger.error);
            System.setOut(Logger.info);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> Logger.LOGGERS.forEach(Logger::close), "shutdown-hook"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(@Nonnull String arg) {
        main(new String[]{arg});
    }
}
