package net.nonswag.tnl.core.api.message.key;

import net.nonswag.tnl.core.api.message.Message;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class SystemMessageKey extends Key {

    @Nonnull
    private static final List<SystemMessageKey> keys = new ArrayList<>();

    @Nonnull
    public static final SystemMessageKey PREFIX = new SystemMessageKey("prefix").register();
    @Nonnull
    public static final SystemMessageKey LOG_INFO = new SystemMessageKey("log-info").register();
    @Nonnull
    public static final SystemMessageKey LOG_WARN = new SystemMessageKey("log-warn").register();
    @Nonnull
    public static final SystemMessageKey LOG_ERROR = new SystemMessageKey("log-error").register();
    @Nonnull
    public static final SystemMessageKey LOG_DEBUG = new SystemMessageKey("log-debug").register();
    @Nonnull
    public static final SystemMessageKey CHAT_FORMAT = new SystemMessageKey("chat-format").register();
    @Nonnull
    public static final SystemMessageKey CHAT_MENTION = new SystemMessageKey("chat-mention").register();

    public SystemMessageKey(@Nonnull String key) {
        super(key);
    }

    @Nonnull
    public SystemMessageKey register() {
        if (!getKeys().contains(this)) getKeys().add(this);
        return this;
    }

    public void unregister() {
        getKeys().remove(this);
    }

    @Nonnull
    public String message() {
        return Message.get(this);
    }

    @Nonnull
    public static List<SystemMessageKey> getKeys() {
        return keys;
    }
}
