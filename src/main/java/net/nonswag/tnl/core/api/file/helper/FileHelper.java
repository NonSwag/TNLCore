package net.nonswag.tnl.core.api.file.helper;

import net.nonswag.tnl.core.api.logger.Logger;
import net.nonswag.tnl.core.utils.LinuxUtil;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class FileHelper {

    private FileHelper() {
    }

    public static void create(@Nonnull File directory, @Nonnull File file) throws IOException {
        create(directory);
        create(file);
    }

    public static void create(@Nonnull File file) throws IOException {
        if (!file.exists()) {
            if (file.getAbsoluteFile().getParentFile().mkdirs()) {
                Logger.debug.println("Generated directories <'" + file.getAbsoluteFile().getParent() + "'>");
            }
            if (file.createNewFile()) {
                Logger.debug.println("Generated file <'" + file.getAbsolutePath() + "'>");
            } else {
                Logger.error.println("Couldn't generate file <'" + file.getAbsolutePath() + "'>");
                throw new FileNotFoundException("Couldn't generate file");
            }
        }
    }

    public static void deleteDirectory(@Nonnull File directory, int tries) {
        for (int i = 0; i < tries && directory.exists(); i++) deleteContentAndSelf(directory);
    }

    public static void deleteDirectory(@Nonnull File directory) {
        deleteDirectory(directory, 1);
    }

    private static void deleteContentAndSelf(@Nonnull File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) deleteContentAndSelf(file);
                    else LinuxUtil.runSafeShellCommand("rm " + file.getAbsolutePath(), null);
                }
            }
            LinuxUtil.runSafeShellCommand("rmdir " + directory.getAbsolutePath(), null);
        } else LinuxUtil.runSafeShellCommand("rm " + directory.getAbsolutePath(), null);
    }
}
