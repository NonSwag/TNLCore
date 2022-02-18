package net.nonswag.tnl.core.api.file.formats;

import lombok.Getter;
import net.nonswag.tnl.core.api.language.Language;
import net.nonswag.tnl.core.api.message.key.Key;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Getter
public class MessageFile extends PropertyFile {

    @Getter
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

    public final void setDefault(@Nonnull Key key, @Nonnull String message) {
        setDefault(key.getKey(), message);
    }

    public final void setDefault(@Nonnull String key, @Nonnull String message) {
        setValueIfAbsent(key, message);
    }

    public final void setMessage(@Nonnull Key key, @Nonnull String message) {
        setMessage(key.getKey(), message);
    }

    public final void setMessage(@Nonnull String key, @Nonnull String message) {
        setValue(key, message);
    }

    @Nullable
    public final String getMessage(@Nonnull Key key) {
        return getMessage(key.getKey());
    }

    @Nullable
    public final String getMessage(@Nonnull String key) {
        return getString(key);
    }

    @Nonnull
    public final String getMessage(@Nonnull Key key, @Nonnull String defaultValue) {
        return getOrDefault(key.getKey(), defaultValue);
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

    @Nullable
    public static MessageFile getFile(@Nonnull Language language) {
        return getList().get(language);
    }

    public static boolean isRegistered(@Nonnull Language language) {
        return getList().containsKey(language);
    }

    @Nonnull
    @Override
    public Charset getCharset() {
        return StandardCharsets.ISO_8859_1;
    }
}
