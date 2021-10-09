package net.nonswag.tnl.core.api.language;

import javax.annotation.Nonnull;

public record LanguageKey(@Nonnull Language language, @Nonnull MessageKey messageKey) {
}
