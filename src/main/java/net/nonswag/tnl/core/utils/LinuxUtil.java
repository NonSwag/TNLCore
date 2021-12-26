package net.nonswag.tnl.core.utils;

import net.nonswag.tnl.core.api.file.formats.ShellFile;
import net.nonswag.tnl.core.api.logger.Logger;
import net.nonswag.tnl.core.api.message.Message;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class LinuxUtil {

    private LinuxUtil() {
    }

    public static void runShellCommand(@Nonnull String command) throws IOException, InterruptedException {
        runShellCommand(command, Logger.debug);
    }

    public static void runShellCommand(@Nonnull String command, @Nonnull Logger logger) throws IOException, InterruptedException {
        runShellCommand(command, null, logger);
    }

    public static void runShellCommand(@Nonnull String command, @Nonnull File directory) throws IOException, InterruptedException {
        runShellCommand(command, directory, Logger.debug);
    }

    public static void runShellCommand(@Nonnull String command, @Nullable File directory, @Nonnull Logger logger) throws IOException, InterruptedException {
        runShellCommand(command.split(" "), directory, logger);
    }

    public static void runShellCommand(@Nonnull String[] command) throws IOException, InterruptedException {
        runShellCommand(command, Logger.debug);
    }

    public static void runShellCommand(@Nonnull String[] command, @Nonnull Logger logger) throws IOException, InterruptedException {
        runShellCommand(command, null, logger);
    }

    public static void runShellCommand(@Nonnull String[] command, @Nonnull File directory) throws IOException, InterruptedException {
        runShellCommand(command, directory, Logger.debug);
    }

    public static void runShellCommand(@Nonnull String[] command, @Nullable File directory, @Nonnull Logger logger) throws IOException, InterruptedException {
        if (command.length == 0) return;
        Process process = directory == null ? Runtime.getRuntime().exec(command) : Runtime.getRuntime().exec(command, null, directory);
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String string;
        while ((string = br.readLine()) != null) logger.println(string);
        process.waitFor();
        logger.println("Finished program with exit code: " + process.exitValue());
    }

    public static void runShellScript(@Nonnull ShellFile script) throws IOException, InterruptedException {
        runShellScript(script, Logger.debug);
    }

    public static void runShellScript(@Nonnull ShellFile script, @Nonnull Logger logger) throws IOException, InterruptedException {
        String[] content = script.getContent();
        for (int i = 0; i < content.length; i++) content[i] = Message.format(content[i], script.getPlaceholders());
        for (String line : content) runShellCommand(line, script.getFile().getAbsoluteFile().getParentFile(), logger);
    }

    public static class Suppressed {

        public static void runShellCommand(@Nonnull String command) {
            runShellCommand(command, Logger.debug);
        }

        public static void runShellCommand(@Nonnull String command, @Nonnull Logger logger) {
            runShellCommand(command, null, logger);
        }

        public static void runShellCommand(@Nonnull String command, @Nonnull File directory) {
            runShellCommand(command, directory, Logger.debug);
        }

        public static void runShellCommand(@Nonnull String command, @Nullable File directory, @Nonnull Logger logger) {
            try {
                LinuxUtil.runShellCommand(command, directory, logger);
            } catch (IOException | InterruptedException e) {
                Logger.error.println(e.getMessage());
            }
        }

        public static void runShellCommand(@Nonnull String[] command) {
            runShellCommand(command, Logger.debug);
        }

        public static void runShellCommand(@Nonnull String[] command, @Nonnull Logger logger) {
            runShellCommand(command, null, logger);
        }

        public static void runShellCommand(@Nonnull String[] command, @Nonnull File directory) {
            runShellCommand(command, directory, Logger.debug);
        }

        public static void runShellCommand(@Nonnull String[] command, @Nullable File directory, @Nonnull Logger logger) {
            try {
                LinuxUtil.runShellCommand(command, directory, logger);
            } catch (IOException | InterruptedException e) {
                Logger.error.println(e.getMessage());
            }
        }

        public static void runShellScript(@Nonnull ShellFile script) {
            runShellScript(script, Logger.debug);
        }

        public static void runShellScript(@Nonnull ShellFile script, @Nonnull Logger logger) {
            try {
                LinuxUtil.runShellScript(script, logger);
            } catch (IOException | InterruptedException e) {
                Logger.error.println(e.getMessage());
            }
        }
    }
}
