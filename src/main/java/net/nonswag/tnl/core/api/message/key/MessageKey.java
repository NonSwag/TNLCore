package net.nonswag.tnl.core.api.message.key;

import net.nonswag.tnl.core.api.language.Language;
import net.nonswag.tnl.core.api.message.Message;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class MessageKey extends Key {

    @Nonnull
    private static final List<MessageKey> keys = new ArrayList<>();

    @Nonnull
    public static final MessageKey NO_PERMISSION = new MessageKey("no-permission").register();
    @Nonnull
    public static final MessageKey COMMAND_ERROR = new MessageKey("command-error").register();
    @Nonnull
    public static final MessageKey TAB_COMPLETE_ERROR = new MessageKey("tab-complete-error").register();
    @Nonnull
    public static final MessageKey CHAT_ERROR = new MessageKey("chat-error").register();
    @Nonnull
    public static final MessageKey UNKNOWN_COMMAND = new MessageKey("unknown-command").register();
    @Nonnull
    public static final MessageKey PLAYER_COMMAND = new MessageKey("player-command").register();
    @Nonnull
    public static final MessageKey CONSOLE_COMMAND = new MessageKey("console-command").register();
    @Nonnull
    public static final MessageKey FIRST_JOIN_MESSAGE = new MessageKey("first-join-message").register();
    @Nonnull
    public static final MessageKey JOIN_MESSAGE = new MessageKey("join-message").register();
    @Nonnull
    public static final MessageKey QUIT_MESSAGE = new MessageKey("quit-message").register();
    @Nonnull
    public static final MessageKey WORLD_SAVED = new MessageKey("world-saved").register();
    @Nonnull
    public static final MessageKey CHANGED_GAMEMODE = new MessageKey("changed-gamemode").register();
    @Nonnull
    public static final MessageKey PLAYER_NOT_ONLINE = new MessageKey("player-not-online").register();
    @Nonnull
    public static final MessageKey NOT_A_PLAYER = new MessageKey("not-a-player").register();
    @Nonnull
    public static final MessageKey UNKNOWN_PLAYER = new MessageKey("unknown-player").register();
    @Nonnull
    public static final MessageKey CONNECTING_TO_SERVER = new MessageKey("connecting-to-server").register();
    @Nonnull
    public static final MessageKey UNKNOWN_SERVER = new MessageKey("unknown-server").register();
    @Nonnull
    public static final MessageKey FAILED_TO_CONNECT_TO_SERVER = new MessageKey("failed-to-connect-to-server").register();
    @Nonnull
    public static final MessageKey SERVER_IS_OFFLINE = new MessageKey("server-is-offline").register();

    public MessageKey(@Nonnull String key) {
        super(key);
    }

    @Nonnull
    public MessageKey register() {
        if (!getKeys().contains(this)) getKeys().add(this);
        return this;
    }

    public void unregister() {
        getKeys().remove(this);
    }

    @Nonnull
    public String message() {
        return Message.get(this, Message.getEnglish());
    }

    @Nonnull
    public String message(@Nonnull Language language) {
        return Message.get(this, language);
    }

    @Nonnull
    public static List<MessageKey> getKeys() {
        return keys;
    }
}
