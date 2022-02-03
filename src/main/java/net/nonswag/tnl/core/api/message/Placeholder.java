package net.nonswag.tnl.core.api.message;

import net.nonswag.tnl.core.api.message.key.SystemMessageKey;
import net.nonswag.tnl.core.api.object.Getter;
import net.nonswag.tnl.core.api.object.MutualGetter;
import net.nonswag.tnl.core.api.platform.PlatformPlayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Placeholder {

    @Nonnull
    private final String placeholder;
    @Nullable
    private final Getter<Object> object;
    @Nullable
    private final MutualGetter<PlatformPlayer, Object> mutualObject;

    public Placeholder(@Nonnull String placeholder, @Nullable Object object) {
        this(placeholder, () -> object == null ? "§8-§7/§8-§r" : object);
    }

    public Placeholder(@Nonnull String placeholder, @Nonnull Getter<Object> object) {
        this.placeholder = placeholder;
        this.mutualObject = null;
        this.object = object;
    }

    public Placeholder(@Nonnull String placeholder, @Nonnull MutualGetter<PlatformPlayer, Object> object) {
        this.placeholder = placeholder;
        this.mutualObject = object;
        this.object = null;
    }

    @Nonnull
    public String placeholder() {
        return placeholder;
    }

    @Nonnull
    public String value() {
        return object == null ? "null" : object.getAsString();
    }

    @Nonnull
    public String value(@Nonnull PlatformPlayer player) {
        return mutualObject == null ? value() : mutualObject.getAsString(player);
    }

    public static class Registry {

        @Nonnull
        private static final HashMap<String, Placeholder> PLACEHOLDERS = new HashMap<>();

        @Nonnull
        public static List<Placeholder> placeholders() {
            return new ArrayList<>(PLACEHOLDERS.values());
        }

        public static boolean isRegistered(@Nonnull String placeholder) {
            return PLACEHOLDERS.containsKey(placeholder);
        }

        public static boolean isRegistered(@Nonnull Placeholder placeholder) {
            return isRegistered(placeholder.placeholder());
        }

        public static void register(@Nonnull Placeholder placeholder) {
            PLACEHOLDERS.put(placeholder.placeholder(), placeholder);
        }

        public static void unregister(@Nonnull String placeholder) {
            PLACEHOLDERS.remove(placeholder);
        }

        @Nullable
        public static Placeholder valueOf(@Nonnull String placeholder) {
            return PLACEHOLDERS.get(placeholder);
        }

        static {
            register(new Placeholder("nl", "\n"));
            register(new Placeholder("prefix", SystemMessageKey.PREFIX.message()));
            register(new Placeholder("time", () -> new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())));
            register(new Placeholder("thread", () -> {
                String name = Thread.currentThread().getName();
                if (name.length() > 20) return name.substring(0, 20) + "...";
                else return name;
            }));
            register(new Placeholder("player", PlatformPlayer::getName));
        }
    }

    @Nonnull
    public String replace(@Nonnull Object value) {
        return value.toString().replace("%" + placeholder() + "%", value());
    }
}
