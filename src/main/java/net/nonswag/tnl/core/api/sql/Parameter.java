package net.nonswag.tnl.core.api.sql;

import net.nonswag.tnl.core.api.object.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Parameter extends Pair<String, String> {

    private Parameter(@Nonnull String identifier, @Nonnull String type) {
        super(identifier, type);
    }

    @Nonnull
    @Override
    public String getValue() {
        assert super.getValue() != null;
        return super.getValue();
    }

    @Override
    public void setKey(@Nonnull String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setValue(@Nullable String value) {
        throw new UnsupportedOperationException();
    }

    @Nonnull
    @Override
    public Parameter duplicate() {
        return of(getKey(), getValue());
    }

    @Nonnull
    public static Parameter of(@Nonnull String identifier, @Nonnull String type) {
        return new Parameter(identifier, type);
    }
}
