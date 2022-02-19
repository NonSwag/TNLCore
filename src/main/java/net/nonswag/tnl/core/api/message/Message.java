//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.nonswag.tnl.core.api.message;

import net.nonswag.tnl.core.api.file.formats.MessageFile;
import net.nonswag.tnl.core.api.file.formats.PropertyFile;
import net.nonswag.tnl.core.api.language.Language;
import net.nonswag.tnl.core.api.logger.Color.Hex;
import net.nonswag.tnl.core.api.logger.Logger;
import net.nonswag.tnl.core.api.message.Placeholder.Registry;
import net.nonswag.tnl.core.api.message.key.Key;
import net.nonswag.tnl.core.api.message.key.MessageKey;
import net.nonswag.tnl.core.api.message.key.SystemMessageKey;
import net.nonswag.tnl.core.api.platform.PlatformPlayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class Message {

    @Nonnull
    private static final MessageFile root = new MessageFile("Core/Messages/", Language.ROOT).register();
    @Nonnull
    private static final MessageFile english = new MessageFile("Core/Messages/", Language.AMERICAN_ENGLISH).register();
    @Nonnull
    private static final MessageFile german = new MessageFile("Core/Messages/", Language.GERMAN).register();

    private Message() {
    }

    @Nonnull
    public static MessageFile getRoot() {
        return root;
    }

    @Nonnull
    public static MessageFile getEnglish() {
        return english;
    }

    @Nonnull
    public static MessageFile getGerman() {
        return german;
    }

    @Nonnull
    public static String get(@Nonnull Key key, @Nonnull MessageFile file) {
        return file.getMessage(key, key.getKey());
    }

    @Nonnull
    public static String get(@Nonnull Key key, @Nonnull Language language) {
        MessageFile file = MessageFile.getFile(language);
        return file != null ? get(key, file) : key.getKey();
    }

    @Nonnull
    public static String get(@Nonnull Key key) {
        return key instanceof SystemMessageKey ? get(key, Language.ROOT) : get(key, Language.AMERICAN_ENGLISH);
    }

    @Nonnull
    public static String format(@Nonnull String text, @Nullable PlatformPlayer player, @Nonnull Placeholder... placeholders) {
        List<String> override = new ArrayList<>();
        for (Placeholder placeholder : placeholders) override.add(placeholder.placeholder());
        for (Placeholder p : Registry.placeholders()) {
            if (override.contains(p.placeholder())) continue;
            if (!text.contains("%" + p.placeholder() + "%")) continue;
            text = text.replace("%" + p.placeholder() + "%", player == null ? p.value() : p.value(player));
        }
        for (Placeholder p : placeholders) {
            if (!text.contains("%" + p.placeholder() + "%")) continue;
            text = text.replace("%" + p.placeholder() + "%", player == null ? p.value() : p.value(player));
        }
        return Hex.colorize(text);
    }

    @Nonnull
    public static String format(@Nonnull String text, @Nonnull Placeholder... placeholders) {
        return format(text, null, placeholders);
    }

    @Nonnull
    public static String format(@Nonnull String text, @Nonnull List<Placeholder> placeholders) {
        return format(text, null, placeholders.toArray(new Placeholder[]{}));
    }

    public static void init() {
        File[] files = new File("Core/Messages/").listFiles((file, s) -> s.endsWith(".locale"));
        List<Language> languages = new ArrayList<>();
        for (File file : files == null ? new File[0] : files) {
            if (Message.class.getClassLoader().getResource("messages/" + file.getName()) == null) {
                String name = null;
                String shorthand = null;
                PropertyFile propertyFile = new PropertyFile(file);
                for (String s : propertyFile.getComments()) {
                    if (s.startsWith(" language -> ") && name == null) {
                        if ((name = s.replace(" language -> ", "")).replace(" ", "").isEmpty()) name = null;
                        else if (shorthand != null) break;
                    } else if (s.startsWith(" shorthand -> ") && shorthand == null) {
                        if ((shorthand = s.replace(" shorthand -> ", "")).replace(" ", "").isEmpty()) shorthand = null;
                        else if (name != null) break;
                    }
                }
                if (name != null && shorthand != null) {
                    languages.add(new Language(name, shorthand));
                } else {
                    Logger.error.println("Can't load MessageFile from <'" + file.getAbsolutePath() + "'>");
                    if (name == null) Logger.error.println("You have to define the name of the language");
                    if (shorthand == null) Logger.error.println("You have to define the shorthand of the language");
                    propertyFile.getComments().clear();
                    propertyFile.addComment(" language -> " + (name == null ? "" : name));
                    propertyFile.addComment(" shorthand -> " + (shorthand == null ? "" : shorthand));
                    propertyFile.save();
                }
            }
        }
        for (Language language : languages) {
            MessageFile file = new MessageFile("Core/Messages/", language).register();
            for (MessageKey key : MessageKey.getKeys()) {
                if (file.getMessage(key) == null) file.setMessage(key, file.getFile().getName() + "$" + key.getKey());
            }
            file.save();
        }
    }
}
