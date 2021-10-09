package net.nonswag.tnl.core.api.message;

import com.google.gson.JsonElement;
import net.nonswag.tnl.core.api.file.formats.JsonFile;
import net.nonswag.tnl.core.api.language.Language;
import net.nonswag.tnl.core.api.language.LanguageKey;
import net.nonswag.tnl.core.api.language.MessageKey;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class Message {

    @Nonnull
    public static final ChatComponent PREFIX = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.PREFIX), "§8[§f§lTNL§8]§r");
    @Nonnull
    public static final ChatComponent LOG_INFO = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.LOG_INFO), "§8[§1%time% §8|§1 Info §8| §1%thread%§8]");
    @Nonnull
    public static final ChatComponent LOG_WARN = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.LOG_WARN), "§8[§e%time% §8|§e Warning §8| §e%thread%§8]");
    @Nonnull
    public static final ChatComponent LOG_ERROR = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.LOG_ERROR), "§8[§4%time% §8|§4 Error §8| §4%thread%§8]");
    @Nonnull
    public static final ChatComponent LOG_DEBUG = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.LOG_DEBUG), "§8[§6%time% §8|§6 Debug §8| §6%thread%§8]");
    @Nonnull
    public static final ChatComponent COLOR_1 = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.COLOR_1), "§6");
    @Nonnull
    public static final ChatComponent COLOR_2 = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.COLOR_2), "§a");
    @Nonnull
    public static final ChatComponent CHAT_FORMAT = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.CHAT_FORMAT), "§8[%color%%world%§8] §f%display_name% §8» %color%%message%");
    @Nonnull
    public static final ChatComponent CHAT_MENTION = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.CHAT_MENTION), "§8(§3%player%§8) %color%");
    @Nonnull
    public static final ChatComponent ITEM_NAME_STANDARD = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.ITEM_NAME_STANDARD), "§8* §3%item_name%");
    @Nonnull
    public static final ChatComponent ITEM_NAME_RARE = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.ITEM_NAME_RARE), "§8* §5%item_name%");
    @Nonnull
    public static final ChatComponent ITEM_NAME_EPIC = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.ITEM_NAME_EPIC), "§8* §d%item_name%");
    @Nonnull
    public static final ChatComponent KICKED_SPAMMING = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.KICKED_SPAMMING), "§cSpamming is prohibited");

    @Nonnull
    public static final ChatComponent NO_PERMISSION_EN = new ChatComponent(new LanguageKey(Language.AMERICAN_ENGLISH, MessageKey.NO_PERMISSION), "%prefix%§c You have no Rights §8(§4%permission%§8)");
    @Nonnull
    public static final ChatComponent COMMAND_ERROR_EN = new ChatComponent(new LanguageKey(Language.AMERICAN_ENGLISH, MessageKey.COMMAND_ERROR), "%prefix%§c Failed to execute command §8(§4%command%§8)");
    @Nonnull
    public static final ChatComponent TAB_COMPLETE_ERROR_EN = new ChatComponent(new LanguageKey(Language.AMERICAN_ENGLISH, MessageKey.TAB_COMPLETE_ERROR), "%prefix%§c Failed to create suggestions §8(§4%command%§8)");
    @Nonnull
    public static final ChatComponent CHAT_ERROR_EN = new ChatComponent(new LanguageKey(Language.AMERICAN_ENGLISH, MessageKey.CHAT_ERROR), "%prefix%§c Failed to send message");
    @Nonnull
    public static final ChatComponent UNKNOWN_COMMAND_EN = new ChatComponent(new LanguageKey(Language.AMERICAN_ENGLISH, MessageKey.UNKNOWN_COMMAND), "%prefix%§c The Command §8(§4%command%§8)§c doesn't exist");
    @Nonnull
    public static final ChatComponent PLAYER_COMMAND_EN = new ChatComponent(new LanguageKey(Language.AMERICAN_ENGLISH, MessageKey.PLAYER_COMMAND), "%prefix%§c This is a player command");
    @Nonnull
    public static final ChatComponent CONSOLE_COMMAND_EN = new ChatComponent(new LanguageKey(Language.AMERICAN_ENGLISH, MessageKey.CONSOLE_COMMAND), "%prefix%§c This is a console command");
    @Nonnull
    public static final ChatComponent KICKED_EN = new ChatComponent(new LanguageKey(Language.AMERICAN_ENGLISH, MessageKey.KICKED), "%prefix% §cYou got kicked%nl%§4%reason%");
    @Nonnull
    public static final ChatComponent FIRST_JOIN_MESSAGE_EN = new ChatComponent(new LanguageKey(Language.AMERICAN_ENGLISH, MessageKey.FIRST_JOIN_MESSAGE), "%prefix%%color_1% %player%%color_2% joined the game §8(§7the first time§8)");
    @Nonnull
    public static final ChatComponent JOIN_MESSAGE_EN = new ChatComponent(new LanguageKey(Language.AMERICAN_ENGLISH, MessageKey.JOIN_MESSAGE), "%prefix%%color_1% %player%%color_2% joined the game");
    @Nonnull
    public static final ChatComponent QUIT_MESSAGE_EN = new ChatComponent(new LanguageKey(Language.AMERICAN_ENGLISH, MessageKey.QUIT_MESSAGE), "%prefix%§4 %player%§c left the game");
    @Nonnull
    public static final ChatComponent WORLD_SAVED_EN = new ChatComponent(new LanguageKey(Language.AMERICAN_ENGLISH, MessageKey.WORLD_SAVED), "%prefix%%color_2% Saved the world %color_1%%world%");
    @Nonnull
    public static final ChatComponent CHANGED_GAMEMODE_EN = new ChatComponent(new LanguageKey(Language.AMERICAN_ENGLISH, MessageKey.CHANGED_GAMEMODE), "%prefix%%color_2% Your gamemode is now %color_1%%gamemode%");
    @Nonnull
    public static final ChatComponent PLAYER_NOT_ONLINE_EN = new ChatComponent(new LanguageKey(Language.AMERICAN_ENGLISH, MessageKey.PLAYER_NOT_ONLINE), "%prefix%§4 %player%§c is not online");
    @Nonnull
    public static final ChatComponent NOT_A_PLAYER_EN = new ChatComponent(new LanguageKey(Language.AMERICAN_ENGLISH, MessageKey.NOT_A_PLAYER), "%prefix%§4 %player%§c is not a player");
    @Nonnull
    public static final ChatComponent CONNECTING_TO_SERVER_EN = new ChatComponent(new LanguageKey(Language.AMERICAN_ENGLISH, MessageKey.CONNECTING_TO_SERVER), "%prefix% §a%color_2%Connecting you to server %color_1%%server%");
    @Nonnull
    public static final ChatComponent UNKNOWN_SERVER_EN = new ChatComponent(new LanguageKey(Language.AMERICAN_ENGLISH, MessageKey.UNKNOWN_SERVER), "%prefix% §cThe server §4%server%§c doesn't exist");
    @Nonnull
    public static final ChatComponent FAILED_TO_CONNECT_TO_SERVER_EN = new ChatComponent(new LanguageKey(Language.AMERICAN_ENGLISH, MessageKey.FAILED_TO_CONNECT_TO_SERVER), "%prefix% §cFailed to connect you to server §4%server%");
    @Nonnull
    public static final ChatComponent SERVER_IS_OFFLINE_EN = new ChatComponent(new LanguageKey(Language.AMERICAN_ENGLISH, MessageKey.SERVER_IS_OFFLINE), "%prefix% §cThe server §4%server%§c is Offline");

    @Nonnull
    public static final ChatComponent NO_PERMISSION_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.NO_PERMISSION), "%prefix%§c Darauf hast du keine Rechte §8(§4%permission%§8)");
    @Nonnull
    public static final ChatComponent COMMAND_ERROR_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.COMMAND_ERROR), "%prefix%§c Fehler beim ausführen des Commands §8(§4%command%§8)");
    @Nonnull
    public static final ChatComponent TAB_COMPLETE_ERROR_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.TAB_COMPLETE_ERROR), "%prefix%§c Fehler beim erstellen der vorschläge §8(§4%command%§8)");
    @Nonnull
    public static final ChatComponent CHAT_ERROR_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.CHAT_ERROR), "%prefix%§c Fehler beim senden der nachricht");
    @Nonnull
    public static final ChatComponent UNKNOWN_COMMAND_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.UNKNOWN_COMMAND), "%prefix%§c Der Command §8(§4%command%§8)§c existiert nicht");
    @Nonnull
    public static final ChatComponent PLAYER_COMMAND_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.PLAYER_COMMAND), "%prefix%§c Das ist ein spieler command");
    @Nonnull
    public static final ChatComponent CONSOLE_COMMAND_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.CONSOLE_COMMAND), "%prefix%§c Das ist ein konsolen command");
    @Nonnull
    public static final ChatComponent KICKED_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.KICKED), "%prefix% §cDu wurdest gekickt%nl%§4%reason%");
    @Nonnull
    public static final ChatComponent FIRST_JOIN_MESSAGE_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.FIRST_JOIN_MESSAGE), "%prefix%%color_1% %player%%color_2% ist dem server beigetreten §8(§7zum ersten mal§8)");
    @Nonnull
    public static final ChatComponent JOIN_MESSAGE_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.JOIN_MESSAGE), "%prefix%%color_1% %player%%color_2% ist dem server beigetreten");
    @Nonnull
    public static final ChatComponent QUIT_MESSAGE_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.QUIT_MESSAGE), "%prefix%§4 %player%§c hat den server verlassen");
    @Nonnull
    public static final ChatComponent WORLD_SAVED_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.WORLD_SAVED), "%prefix%%color_2% Die welt %color_1%%world%%color_2% wurde gespeichert");
    @Nonnull
    public static final ChatComponent CHANGED_GAMEMODE_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.CHANGED_GAMEMODE), "%prefix%%color_2% Dein gamemode ist jetzt %color_1%%gamemode%");
    @Nonnull
    public static final ChatComponent PLAYER_NOT_ONLINE_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.PLAYER_NOT_ONLINE), "%prefix%§4 %player%§c ist nicht online");
    @Nonnull
    public static final ChatComponent NOT_A_PLAYER_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.NOT_A_PLAYER), "%prefix%§4 %player%§c ist kein spieler");
    @Nonnull
    public static final ChatComponent CONNECTING_TO_SERVER_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.CONNECTING_TO_SERVER), "%prefix% %color_2%Verbinde dich zum server %color_1%%server%");
    @Nonnull
    public static final ChatComponent UNKNOWN_SERVER_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.UNKNOWN_SERVER), "%prefix% §cDer server §4%server%§c existiert nicht");
    @Nonnull
    public static final ChatComponent FAILED_TO_CONNECT_TO_SERVER_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.FAILED_TO_CONNECT_TO_SERVER), "%prefix% §cDu konntest nicht zum server §4%server%§c verbunden werden");
    @Nonnull
    public static final ChatComponent SERVER_IS_OFFLINE_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.SERVER_IS_OFFLINE), "%prefix% §cDer server §4%server% ist Offline");

    private Message() {
    }

    @Nullable
    public static ChatComponent valueOf(@Nonnull LanguageKey languageKey) {
        for (ChatComponent message : ChatComponent.getMessages()) {
            if (message.getLanguageKey().equals(languageKey)) return message;
        }
        return null;
    }

    public static void init() {
        for (Language language : Language.getLanguages()) {
            JsonFile jsonFile = new JsonFile("Core/Messages/", language.getFile());
            for (MessageKey key : MessageKey.getKeys()) {
                if ((key.isSystemMessage() && !language.equals(Language.ROOT)) || (!key.isSystemMessage() && language.equals(Language.ROOT))) {
                    continue;
                }
                boolean exists = false;
                JsonElement element = jsonFile.getJsonElement().getAsJsonObject().get(key.getKey());
                if (jsonFile.getJsonElement().getAsJsonObject().has(key.getKey())) {
                    for (ChatComponent message : ChatComponent.getMessages()) {
                        if (language.equals(message.getLanguageKey().language()) && message.getLanguageKey().messageKey().equals(key)) {
                            String value = element.getAsString();
                            if (value != null) message.setText(value);
                            else {
                                message.setText(message.text());
                                jsonFile.getJsonElement().getAsJsonObject().addProperty(key.getKey(), message.text());
                            }
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        String value = element.getAsString();
                        if (value != null) new ChatComponent(new LanguageKey(language, key), value);
                    }
                } else {
                    for (ChatComponent message : ChatComponent.getMessages()) {
                        if (message.getLanguageKey().language().equals(language) && message.getLanguageKey().messageKey().equals(key)) {
                            jsonFile.getJsonElement().getAsJsonObject().addProperty(key.getKey(), message.text());
                            break;
                        }
                    }
                }
            }
            jsonFile.save();
        }
    }
}
