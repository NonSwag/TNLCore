package net.nonswag.tnl.core.api.logger;

import net.nonswag.tnl.core.api.message.Message;
import net.nonswag.tnl.core.api.message.key.SystemMessageKey;
import net.nonswag.tnl.core.api.object.Condition;
import net.nonswag.tnl.core.api.object.Getter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Logger {

    @Nonnull
    private static final PrintStream out = new PrintStream(new FileOutputStream(FileDescriptor.out), true);
    @Nonnull
    private static final PrintStream err = new PrintStream(new FileOutputStream(FileDescriptor.err), true);

    @Nonnull
    public static final Logger info = new Logger("info", () -> SystemMessageKey.LOG_INFO.message(), out).colorize(Color.LIME, Color.GOLD);
    @Nonnull
    public static final Logger warn = new Logger("warn", () -> SystemMessageKey.LOG_WARN.message(), out).colorize(Color.YELLOW, Color.WHITE);
    @Nonnull
    public static final Logger debug = new Logger("debug", () -> SystemMessageKey.LOG_DEBUG.message(), out).colorize(Color.YELLOW, Color.GOLD);
    @Nonnull
    public static final Logger error = new Logger("error", () -> SystemMessageKey.LOG_ERROR.message(), err).colorize(Color.RED, Color.DARK_RED);

    @Nonnull
    private final String name;
    @Nonnull
    private final Getter<String> prefix;
    @Nonnull
    private Color mainColor = Color.RESET;
    @Nonnull
    private Color secondaryColor = Color.RESET;
    @Nonnull
    private final PrintStream printStream;
    @Nonnull
    private Condition condition = () -> true;

    public Logger(@Nonnull String name, @Nonnull Getter<String> prefix, @Nonnull PrintStream printStream) {
        this.name = name;
        this.prefix = prefix;
        this.printStream = printStream;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public Getter<String> getPrefix() {
        return prefix;
    }

    @Nonnull
    public Color getMainColor() {
        return mainColor;
    }

    @Nonnull
    public Color getSecondaryColor() {
        return secondaryColor;
    }

    @Nonnull
    private PrintStream getPrintStream() {
        return printStream;
    }

    @Nonnull
    public Condition getCondition() {
        return condition;
    }

    @Nonnull
    public Logger setMainColor(@Nonnull Color mainColor) {
        this.mainColor = mainColor;
        return this;
    }

    @Nonnull
    public Logger setSecondaryColor(@Nonnull Color secondaryColor) {
        this.secondaryColor = secondaryColor;
        return this;
    }

    @Nonnull
    public Logger setCondition(@Nonnull Condition condition) {
        this.condition = condition;
        return this;
    }

    @Nonnull
    public Logger colorize(@Nonnull Color mainColor, @Nonnull Color secondaryColor) {
        return setMainColor(mainColor).setSecondaryColor(secondaryColor);
    }

    private void printStackTrace(@Nonnull Throwable throwable) {
        StackTraceElement[] trace = throwable.getStackTrace();
        for (StackTraceElement element : trace) println("\tat " + element);
        Throwable[] suppressed = throwable.getSuppressed();
        for (Throwable element : suppressed) println("Suppressed: \t" + element);
        Throwable cause = throwable.getCause();
        if (cause != null) printCause(cause);
    }

    private void printCause(@Nonnull Throwable cause) {
        println("Caused by: " + cause);
        StackTraceElement[] trace = cause.getStackTrace();
        for (StackTraceElement element : trace) println("\tat " + element);
        Throwable[] suppressed = cause.getSuppressed();
        for (Throwable element : suppressed) println("Suppressed: \t" + element);
        cause = cause.getCause();
        if (cause != null) printCause(cause);
    }

    public void println(@Nonnull Object... values) {
        if (!getCondition().check()) return;
        for (@Nullable Object value : values) {
            if (value == null) continue;
            if (value instanceof Throwable throwable) {
                println(throwable.getClass().getSimpleName() + ": " + throwable.getMessage());
                printStackTrace(throwable);
            } else {
                String string = Color.Hex.colorize(value.toString());
                String text = getMainColor().getCode() + string.replace(".", "§8.%1%").
                        replace(",", "§8,%1%").replace("<'", "§8'%2%").replace("'>", "§8'%1%").
                        replace(":", "§8:%2%").replace("[", "§8[%2%").replace("]", "§8]%1%").
                        replace("(", "§8(%2%").replace(")", "§8)%1%").replace("{", "§8{%2%").
                        replace("}", "§8}%1%").replace("\"", "§8\"%1%").replace("/", "§8/%2%").
                        replace("\\", "§8\\%2%").replace("|", "§8|%2%").replace(">", "§8>%1%").
                        replace("<", "§8<%1%").replace("»", "§8»%1%").replace("«", "§8«%1%").
                        replace("%1%", getMainColor().getCode()).replace("%2%", getSecondaryColor().getCode());
                String prefix = Color.replace(getPrefix().get());
                if (prefix.isEmpty()) getPrintStream().println(Message.format(Color.replace(text + "§r")));
                else getPrintStream().println(Color.replace(Message.format(prefix + " " + text + "§r")));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logger logger = (Logger) o;
        return name.equals(logger.name);
    }
}
