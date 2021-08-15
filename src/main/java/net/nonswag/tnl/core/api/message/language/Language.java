package net.nonswag.tnl.core.api.message.language;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Language {

    @Nonnull
    private static final List<Language> languages = new ArrayList<>();

    @Nonnull
    public static final Language ROOT = new Language("system");
    @Nonnull
    public static final Language UNKNOWN = new Language("Unknown");

    @Nonnull
    public static final Language GERMAN = new Language("German", "de_de", "DE");
    @Nonnull
    public static final Language AMERICAN_ENGLISH = new Language("American English", "en_us", "EN");

    @Nonnull
    private final String name;
    @Nonnull
    private final String shorthand;
    @Nonnull
    private final String key;
    @Nonnull
    private final String file;

    Language(@Nonnull String name) {
        this(name, "");
    }

    Language(@Nonnull String name, @Nonnull String shorthand) {
        this(name, shorthand, "");
    }

    Language(@Nonnull String name, @Nonnull String shorthand, @Nonnull String key) {
        this.name = name;
        this.shorthand = shorthand;
        this.key = key;
        this.file = ("messages" + (getShorthand().isEmpty() ? "" : "-" + getShorthand()) + ".json");
        getLanguages().add(this);
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public String getShorthand() {
        return shorthand;
    }

    @Nonnull
    public String getKey() {
        return key;
    }

    @Nonnull
    public String getFile() {
        return file;
    }

    @Nonnull
    public static Language fromLocale(@Nonnull String locale) {
        try {
            for (Language language : getLanguages()) {
                if (language.getShorthand().equalsIgnoreCase(locale) || language.getName().equalsIgnoreCase(locale)) {
                    return language;
                }
            }
        } catch (IllegalArgumentException | NullPointerException ignored) {
        }
        return Language.UNKNOWN;
    }

    @Nonnull
    public static List<Language> getLanguages() {
        return languages;
    }
}
