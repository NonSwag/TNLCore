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

public final class LinuxUtil {

    private LinuxUtil() {
    }

    public static int runShellCommandUnmodified(@Nonnull String command) throws IOException, InterruptedException {
        return runShellCommandUnmodified(command, Logger.debug);
    }

    public static int runShellCommandUnmodified(@Nonnull String command, @Nonnull Logger logger) throws IOException, InterruptedException {
        return runShellCommandUnmodified(command, null, logger);
    }

    public static int runShellCommandUnmodified(@Nonnull String command, @Nonnull File directory) throws IOException, InterruptedException {
        return runShellCommandUnmodified(command, directory, Logger.debug);
    }

    public static int runShellCommandUnmodified(@Nonnull String command, @Nullable File directory, @Nonnull Logger logger) throws IOException, InterruptedException {
        return runShellCommand(new String[]{command}, directory, logger);
    }

    public static int runShellCommand(@Nonnull String command) throws IOException, InterruptedException {
        return runShellCommand(command, Logger.debug);
    }

    public static int runShellCommand(@Nonnull String command, @Nonnull Logger logger) throws IOException, InterruptedException {
        return runShellCommand(command, null, logger);
    }

    public static int runShellCommand(@Nonnull String command, @Nonnull File directory) throws IOException, InterruptedException {
        return runShellCommand(command, directory, Logger.debug);
    }

    public static int runShellCommand(@Nonnull String command, @Nullable File directory, @Nonnull Logger logger) throws IOException, InterruptedException {
        return runShellCommand(command.split(" "), directory, logger);
    }

    private static int runShellCommand(@Nonnull String[] command) throws IOException, InterruptedException {
        return runShellCommand(command, null, Logger.debug);
    }

    private static int runShellCommand(@Nonnull String[] command, @Nullable File directory, @Nonnull Logger logger) throws IOException, InterruptedException {
        if (command.length == 0) return -1;
        Process process;
        if (directory == null) {
            if (command.length == 1) process = Runtime.getRuntime().exec(command[0]);
            else process = Runtime.getRuntime().exec(command);
        } else {
            if (command.length == 1) process = Runtime.getRuntime().exec(command[0], null, directory);
            else process = Runtime.getRuntime().exec(command, null, directory);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String string;
        while ((string = br.readLine()) != null) logger.println(string);
        process.waitFor();
        logger.println("Finished program with exit code: " + process.exitValue());
        return process.exitValue();
    }

    public static int runShellScript(@Nonnull ShellFile script) throws IOException, InterruptedException {
        return runShellScript(script, Logger.debug);
    }

    public static int runShellScript(@Nonnull ShellFile script, @Nonnull Logger logger) throws IOException, InterruptedException {
        String[] content = script.getContent();
        for (int i = 0; i < content.length; i++) content[i] = Message.format(content[i], script.getPlaceholders());
        return runShellCommand(String.join("\n", content), script.getFile().getAbsoluteFile().getParentFile(), logger);
    }

    public static class Suppressed {

        public static int runShellCommandUnmodified(@Nonnull String command) {
            return runShellCommandUnmodified(command, Logger.debug);
        }

        public static int runShellCommandUnmodified(@Nonnull String command, @Nonnull Logger logger) {
            return runShellCommandUnmodified(command, null, logger);
        }

        public static int runShellCommandUnmodified(@Nonnull String command, @Nonnull File directory) {
            return runShellCommandUnmodified(command, directory, Logger.debug);
        }

        public static int runShellCommandUnmodified(@Nonnull String command, @Nullable File directory, @Nonnull Logger logger) {
            try {
                return LinuxUtil.runShellCommandUnmodified(command, directory, logger);
            } catch (IOException | InterruptedException e) {
                Logger.error.println(e.getMessage());
                return -1;
            }
        }

        public static int runShellCommand(@Nonnull String command) {
            return runShellCommand(command, Logger.debug);
        }

        public static int runShellCommand(@Nonnull String command, @Nonnull Logger logger) {
            return runShellCommand(command, null, logger);
        }

        public static int runShellCommand(@Nonnull String command, @Nonnull File directory) {
            return runShellCommand(command, directory, Logger.debug);
        }

        public static int runShellCommand(@Nonnull String command, @Nullable File directory, @Nonnull Logger logger) {
            try {
                return LinuxUtil.runShellCommand(command, directory, logger);
            } catch (IOException | InterruptedException e) {
                Logger.error.println(e.getMessage());
                return -1;
            }
        }

        public static int runShellScript(@Nonnull ShellFile script) {
            return runShellScript(script, Logger.debug);
        }

        public static int runShellScript(@Nonnull ShellFile script, @Nonnull Logger logger) {
            try {
                return LinuxUtil.runShellScript(script, logger);
            } catch (IOException | InterruptedException e) {
                Logger.error.println(e.getMessage());
                return -1;
            }
        }
    }
}
