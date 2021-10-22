package net.nonswag.tnl.core.api.file.formats;

import net.nonswag.tnl.core.api.language.Language;
import net.nonswag.tnl.core.api.message.key.Key;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

public class MessageFile extends PropertyFile {

    @Nonnull
    private static final HashMap<Language, MessageFile> list = new HashMap<>();
    @Nonnull
    private final Language language;

    public MessageFile(@Nonnull String path, @Nonnull String name, @Nonnull Language language) {
        super(path, name + ".locale");
        this.language = language;
    }

    public MessageFile(@Nonnull String path, @Nonnull Language language) {
        this(path, language.name().toLowerCase().replace(" ", "-"), language);
    }

    @Nonnull
    public final Language getLanguage() {
        return this.language;
    }

    public final void setDefault(@Nonnull Key key, @Nonnull String message) {
        this.setDefault(key.getKey(), message);
    }

    public final void setDefault(@Nonnull String key, @Nonnull String message) {
        this.setValueIfAbsent(key, message);
    }

    public final void setMessage(@Nonnull Key key, @Nonnull String message) {
        this.setMessage(key.getKey(), message);
    }

    public final void setMessage(@Nonnull String key, @Nonnull String message) {
        this.setValue(key, message);
    }

    @Nullable
    public final String getMessage(@Nonnull Key key) {
        return this.getMessage(key.getKey());
    }

    @Nullable
    public final String getMessage(@Nonnull String key) {
        return this.getString(key);
    }

    @Nonnull
    public final String getMessage(@Nonnull Key key, @Nonnull String defaultValue) {
        return this.getOrDefault(key.getKey(), defaultValue);
    }

    @Nonnull
    public final String getMessage(@Nonnull String key, @Nonnull String defaultValue) {
        String message = getMessage(key);
        return message != null ? message : defaultValue;
    }

    @Nonnull
    public final MessageFile register() {
        getList().putIfAbsent(this.getLanguage(), this);
        return this;
    }

    public final void unregister() {
        getList().remove(this.getLanguage(), this);
    }

    @Nonnull
    private static HashMap<Language, MessageFile> getList() {
        return list;
    }

    @Nullable
    public static MessageFile getFile(@Nonnull Language language) {
        return getList().get(language);
    }

    public static boolean isRegistered(@Nonnull Language language) {
        return getList().containsKey(language);
    }
}
