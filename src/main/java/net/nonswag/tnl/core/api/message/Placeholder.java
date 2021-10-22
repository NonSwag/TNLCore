package net.nonswag.tnl.core.api.message;

import net.nonswag.tnl.core.api.logger.Logger;
import net.nonswag.tnl.core.api.message.formulary.Formulary;
import net.nonswag.tnl.core.api.message.formulary.PlayerFormulary;
import net.nonswag.tnl.core.api.message.formulary.VoidFormulary;
import net.nonswag.tnl.core.api.message.key.SystemMessageKey;
import net.nonswag.tnl.core.api.platform.PlatformPlayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public record Placeholder(@Nonnull String placeholder, @Nonnull Object object) {

    public static class Registry {

        @Nonnull
        private static final HashMap<String, String> PLACEHOLDERS = new HashMap<>();
        @Nonnull
        private static final List<Formulary<?>> FORMULARY = new ArrayList<>();

        @Nonnull
        static List<String> values() {
            return new ArrayList<>(PLACEHOLDERS.keySet());
        }

        @Nonnull
        static List<Formulary<?>> formularies() {
            return new ArrayList<>(FORMULARY);
        }

        public static boolean isRegistered(@Nonnull String placeholder) {
            return PLACEHOLDERS.containsKey(placeholder);
        }

        public static void register(@Nonnull Placeholder placeholder) {
            if (!PLACEHOLDERS.containsKey(placeholder.placeholder())) {
                PLACEHOLDERS.put(placeholder.placeholder(), placeholder.object().toString());
            } else {
                Logger.error.println("A static placeholder named <'" + placeholder.placeholder() + "'> is already registered");
            }
        }

        public static void unregister(@Nonnull String placeholder) {
            if (PLACEHOLDERS.containsKey(placeholder)) {
                PLACEHOLDERS.remove(placeholder);
            } else Logger.error.println("A static placeholder named <'" + placeholder + "'> is not registered");
        }

        public static void updateValue(@Nonnull Placeholder placeholder) {
            if (PLACEHOLDERS.containsKey(placeholder.placeholder())) {
                PLACEHOLDERS.put(placeholder.placeholder(), placeholder.object().toString());
            } else Logger.error.println("A static placeholder named <'" + placeholder + "'> is not registered");
        }

        @Nullable
        public static Placeholder valueOf(@Nonnull String placeholder) {
            if (isRegistered(placeholder)) return new Placeholder(placeholder, PLACEHOLDERS.get(placeholder));
            else return null;
        }

        static {
            register(new Placeholder("nl", "\n"));

            register(new VoidFormulary() {
                @Nonnull
                @Override
                public Placeholder check(@Nullable Void value) {
                    return new Placeholder("prefix", SystemMessageKey.PREFIX.message());
                }
            });
            register(new VoidFormulary() {
                @Nonnull
                @Override
                public Placeholder check(@Nullable Void value) {
                    return new Placeholder("time", new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
                }
            });
            register(new VoidFormulary() {
                @Nonnull
                @Override
                public Placeholder check(@Nullable Void value) {
                    return new Placeholder("thread", Thread.currentThread().getName());
                }
            });

            register(new PlayerFormulary() {
                @Nonnull
                @Override
                public Placeholder check(@Nonnull PlatformPlayer player) {
                    return new Placeholder("player", player.getName());
                }
            });
        }

        public static <V> void register(@Nonnull Formulary<V> formulary) {
            if (!FORMULARY.contains(formulary)) FORMULARY.add(formulary);
            else Logger.error.println("This dynamic placeholder is already registered");
        }

        public static void unregister(@Nonnull Formulary<?> formulary) {
            if (FORMULARY.contains(formulary)) FORMULARY.remove(formulary);
            else Logger.error.println("This dynamic placeholder is not registered");
        }
    }

    public Placeholder(@Nonnull String placeholder, @Nullable Object object) {
        this.placeholder = placeholder;
        this.object = (object == null ? "§8-§7/§8-§r" : object);
    }

    @Nonnull
    public String replace(@Nonnull Object value) {
        return value.toString().replace("%" + placeholder() + "%", object().toString());
    }

    @Nonnull
    public static String replace(@Nonnull Object value, @Nonnull Placeholder... placeholders) {
        String string = value.toString();
        for (Placeholder placeholder : placeholders) {
            string = placeholder.replace(string);
        }
        return string;
    }
}
