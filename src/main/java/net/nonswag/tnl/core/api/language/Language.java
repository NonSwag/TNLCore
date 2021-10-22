package net.nonswag.tnl.core.api.language;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public record Language(@Nonnull String name, @Nonnull String shorthand) {

    @Nonnull
    private static final List<Language> languages = new ArrayList<>();

    @Nonnull
    public static final Language ROOT = (new Language("system", "")).register();
    @Nonnull
    public static final Language UNKNOWN = new Language("unknown", "");
    @Nonnull
    public static final Language GERMAN = (new Language("German", "de_de")).register();
    @Nonnull
    public static final Language AMERICAN_ENGLISH = (new Language("American English", "en_us")).register();

    public Language(@Nonnull String name, @Nonnull String shorthand) {
        this.name = name;
        this.shorthand = shorthand;
    }

    @Nonnull
    public Language register() {
        if (!getLanguages().contains(this)) getLanguages().add(this);
        return this;
    }

    public void unregister() {
        getLanguages().remove(this);
    }

    @Nonnull
    @Override
    public String toString() {
        return name() + (shorthand().isEmpty() ? "" : ", " + shorthand());
    }

    @Nonnull
    public static Language fromLocale(@Nonnull String locale) {
        for (Language language : getLanguages()) {
            if (language.shorthand().equalsIgnoreCase(locale) || language.name().equalsIgnoreCase(locale)) {
                return language;
            }
        }
        return Language.UNKNOWN;
    }

    @Nonnull
    public static List<Language> getLanguages() {
        return languages;
    }
}
