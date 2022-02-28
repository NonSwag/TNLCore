package net.nonswag.tnl.core.api.file.helper;

import net.nonswag.tnl.core.api.logger.Logger;
import net.nonswag.tnl.core.utils.LinuxUtil;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public final class FileHelper {

    private FileHelper() {
    }

    public static void create(@Nonnull File directory, @Nonnull File file) throws IOException {
        create(directory);
        create(file);
    }

    public static void create(@Nonnull File file) throws IOException {
        if (file.exists()) return;
        if (!file.getName().contains(".")) file.getAbsoluteFile().mkdirs();
        else {
            file.getAbsoluteFile().getParentFile().mkdirs();
            if (!file.createNewFile()) throw new FileNotFoundException("Couldn't generate file");
        }
    }

    public static void createSilent(@Nonnull File file) {
        try {
            create(file);
        } catch (IOException e) {
            Logger.error.println("Failed to create file <'" + file.getAbsolutePath() + "'>", e);
        }
    }

    public static void createLink(@Nonnull File from, @Nonnull File to) {
        try {
            from = from.getAbsoluteFile();
            to = to.getAbsoluteFile();
            if (!from.exists()) Files.createSymbolicLink(from.toPath(), to.toPath());
        } catch (IOException e) {
            Logger.error.println("Failed to create link between <'" + from.getAbsolutePath() + "'> and <'" + to.getAbsoluteFile() + "'>", e);
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
                    else LinuxUtil.Suppressed.runShellCommand("rm " + file.getAbsolutePath());
                }
            }
            LinuxUtil.Suppressed.runShellCommand("rmdir " + directory.getAbsolutePath());
        } else LinuxUtil.Suppressed.runShellCommand("rm " + directory.getAbsolutePath());
    }

    public static void copyResourceFile(@Nonnull Class<?> clazz, @Nonnull String resource, @Nonnull String destination, boolean override) {
        File to = new File(destination).getAbsoluteFile();
        FileHelper.createSilent(to);
        File file = new File(to, new File(resource).getName());
        if (override || !file.exists()) {
            InputStream from = clazz.getClassLoader().getResourceAsStream(resource);
            if (from != null) {
                try {
                    Files.copy(from, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else System.err.println("resource file not found (" + resource + ")");
        }
    }
}
