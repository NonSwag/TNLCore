package net.nonswag.tnl.core.api.message;

import net.nonswag.tnl.core.api.language.LanguageKey;
import net.nonswag.tnl.core.api.logger.Color;
import net.nonswag.tnl.core.api.logger.Logger;
import net.nonswag.tnl.core.api.message.formulary.Formulary;
import net.nonswag.tnl.core.api.message.formulary.PlayerFormulary;
import net.nonswag.tnl.core.api.message.formulary.VoidFormulary;
import net.nonswag.tnl.core.api.platform.PlatformPlayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ChatComponent {

    @Nonnull
    private static final List<ChatComponent> messages = new ArrayList<>();

    @Nonnull
    private final LanguageKey languageKey;
    @Nonnull
    private String text = "";

    public ChatComponent(@Nonnull LanguageKey languageKey, @Nonnull String text) {
        this.languageKey = languageKey;
        setText(text);
        if (!getMessages().contains(this)) getMessages().add(this);
    }

    @Nonnull
    public LanguageKey getLanguageKey() {
        return languageKey;
    }

    @Nonnull
    public String text() {
        return this.text;
    }

    @Nonnull
    public String getText() {
        return this.getText((PlatformPlayer) null);
    }

    public void setText(@Nonnull String text) {
        this.text = text;
    }

    @Nonnull
    public String getText(@Nullable PlatformPlayer player, @Nonnull Placeholder... placeholders) {
        return ChatComponent.getText(this.text, player, placeholders);
    }

    @Nonnull
    public String getText(@Nonnull Placeholder... placeholders) {
        return this.getText((PlatformPlayer) null, placeholders);
    }

    @Nonnull
    public static String getText(@Nonnull String text, @Nullable PlatformPlayer player, @Nonnull Placeholder... placeholders) {
        for (Placeholder placeholder : placeholders) {
            text = text.replace("%" + placeholder.placeholder() + "%", placeholder.object().toString());
        }
        for (String value : Placeholder.Registry.values()) {
            Placeholder placeholder = Placeholder.Registry.valueOf(value);
            if (placeholder != null) {
                text = text.replace("%" + placeholder.placeholder() + "%", placeholder.object().toString());
            } else Logger.error.println("Cannot find placeholder <'" + value + "'> but it is registered");
        }
        for (Formulary<?> formulary : Placeholder.Registry.formularies()) {
            Placeholder check = null;
            if (formulary instanceof PlayerFormulary) {
                if (player != null) check = ((PlayerFormulary) formulary).check(player);
            } else if (formulary instanceof VoidFormulary) {
                check = ((VoidFormulary) formulary).check();
            } else {
                Logger.error.println("Formulary Instance <'" + formulary.getClass().getName() + "'> is not registered");
            }
            if (check != null) text = text.replace("%" + check.placeholder() + "%", check.object().toString());
        }
        return Color.Hex.colorize(text);
    }

    @Nonnull
    public static String getText(@Nonnull String text, @Nonnull Placeholder... placeholders) {
        return getText(text, null, placeholders);
    }

    @Nonnull
    public static List<ChatComponent> getMessages() {
        return messages;
    }
}
