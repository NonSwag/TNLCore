package net.nonswag.tnl.core.api.file.helper;

import net.nonswag.tnl.core.api.errors.file.FileCreateException;
import net.nonswag.tnl.core.api.errors.file.FileDeleteException;
import net.nonswag.tnl.core.api.errors.file.FileException;
import net.nonswag.tnl.core.api.errors.file.FileLinkException;
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

    public static void create(@Nonnull File directory, @Nonnull File file) throws FileCreateException {
        createDirectory(directory);
        create(file);
    }

    public static boolean createDirectory(@Nonnull File directory) throws FileCreateException {
        try {
            if (directory.exists()) return true;
            directory.getAbsoluteFile().mkdirs();
            return directory.exists();
        } catch (Exception e) {
            throw new FileCreateException(e);
        }
    }

    public static boolean createFile(@Nonnull File file) throws FileCreateException {
        try {
            file = file.getAbsoluteFile();
            file.getParentFile().mkdirs();
            file.createNewFile();
            return file.exists();
        } catch (IOException e) {
            throw new FileCreateException(e);
        }
    }

    public static boolean create(@Nonnull File file) throws FileCreateException {
        return file.getName().contains(".") ? createFile(file) : createDirectory(file);
    }

    public static void createLink(@Nonnull File from, @Nonnull File to) throws FileLinkException {
        try {
            from = from.getAbsoluteFile();
            to = to.getAbsoluteFile();
            if (!from.exists()) Files.createSymbolicLink(from.toPath(), to.toPath());
        } catch (IOException e) {
            throw new FileLinkException(e);
        }
    }


    private static boolean delete(@Nonnull File file) throws FileDeleteException {
        try {
            if (!file.exists()) return true;
            if (file.isDirectory()) return LinuxUtil.runShellCommand("rm -r " + file.getAbsolutePath()) == 0;
            else return file.delete();
        } catch (IOException | InterruptedException e) {
            throw new FileDeleteException(e);
        }
    }

    public static boolean copyResourceFile(@Nonnull Class<?> clazz, @Nonnull String resource, @Nonnull String destination, boolean override) throws FileException {
        try {
            File to = new File(destination).getAbsoluteFile();
            FileHelper.createDirectory(to);
            File file = new File(to, new File(resource).getName());
            if (file.exists() && !override) return true;
            InputStream from = clazz.getClassLoader().getResourceAsStream(resource);
            if (from != null) Files.copy(from, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            else throw new FileNotFoundException("resource file not found (%s)".formatted(resource));
        } catch (IOException e) {
            throw new FileCreateException(e);
        }
        return true;
    }
}
