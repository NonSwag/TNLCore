package net.nonswag.tnl.core.api.logger;

import net.nonswag.tnl.core.api.message.Message;
import net.nonswag.tnl.core.api.message.key.SystemMessageKey;
import net.nonswag.tnl.core.api.object.Condition;
import net.nonswag.tnl.core.api.object.Duplicable;
import net.nonswag.tnl.core.api.object.Getter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Logger extends PrintStream implements Duplicable {

    @Nonnull
    public static final Logger info = new Logger("info", SystemMessageKey.LOG_INFO::message, FileDescriptor.out).colorize(Color.LIME, Color.GOLD);
    @Nonnull
    public static final Logger warn = new Logger("warn", SystemMessageKey.LOG_WARN::message, FileDescriptor.out).colorize(Color.YELLOW, Color.WHITE);
    @Nonnull
    public static final Logger debug = new Logger("debug", SystemMessageKey.LOG_DEBUG::message, FileDescriptor.out).colorize(Color.YELLOW, Color.GOLD);
    @Nonnull
    public static final Logger tip = new Logger("tip", SystemMessageKey.LOG_TIP::message, FileDescriptor.out).colorize(Color.WHITE, Color.LIME);
    @Nonnull
    public static final Logger error = new Logger("error", SystemMessageKey.LOG_ERROR::message, FileDescriptor.err).colorize(Color.RED, Color.DARK_RED);

    @Nonnull
    private final String name;
    @Nonnull
    private final Getter<String> prefix;
    @Nonnull
    private Color mainColor = Color.RESET;
    @Nonnull
    private Color secondaryColor = Color.RESET;
    @Nonnull
    private Condition condition = () -> true;
    @Nonnull
    private final FileDescriptor descriptor;

    public Logger(@Nonnull String name, @Nonnull Getter<String> prefix, @Nonnull FileDescriptor descriptor) {
        super(new FileOutputStream(descriptor), true);
        this.name = name;
        this.prefix = prefix;
        this.descriptor = descriptor;
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
    public Condition getCondition() {
        return condition;
    }

    @Nonnull
    public FileDescriptor getDescriptor() {
        return descriptor;
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
        List<StackTraceElement> trace = Arrays.asList(throwable.getStackTrace());
        for (Throwable t : throwable.getSuppressed()) {
            for (StackTraceElement element : t.getStackTrace()) trace.removeIf(element::equals);
        }
        for (StackTraceElement element : trace) println("\tat " + element);
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


    @Override
    public void println() {
        super.println();
    }

    @Override
    public void println(boolean x) {
        _println(x);
    }

    @Override
    public void println(char x) {
        _println(x);
    }

    @Override
    public void println(int x) {
        _println(x);
    }

    @Override
    public void println(long x) {
        _println(x);
    }

    @Override
    public void println(float x) {
        _println(x);
    }

    @Override
    public void println(double x) {
        _println(x);
    }

    @Override
    public void println(@Nonnull char[] x) {
        _println(Arrays.toString(x));
    }

    @Override
    public void println(@Nullable String x) {
        _println(x);
    }

    @Override
    public void println(@Nullable Object x) {
        _println(x);
    }

    public void println(@Nonnull Object... values) {
        _println(values);
    }

    @Override
    public PrintStream append(char c) {
        println(c);
        return this;
    }

    private void _println(@Nonnull Object... values) {
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
                if (prefix.isEmpty()) super.println(Message.format(Color.replace(text + "§r")));
                else super.println(Color.replace(Message.format(prefix + " " + text + "§r")));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logger logger = (Logger) o;
        return name.equals(logger.name) && prefix.equals(logger.prefix) && mainColor == logger.mainColor && secondaryColor == logger.secondaryColor && condition.equals(logger.condition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, prefix, mainColor, secondaryColor, condition);
    }

    @Nonnull
    @Override
    public Logger duplicate() {
        Logger logger = new Logger(getName() + " (copy)", getPrefix(), getDescriptor());
        logger.setCondition(getCondition()).setMainColor(getMainColor()).setSecondaryColor(getSecondaryColor());
        return logger;
    }
}
