package net.nonswag.tnl.core.api.reflection;

import net.nonswag.tnl.core.api.errors.TNLRuntimeException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ReflectionException extends TNLRuntimeException {

    public ReflectionException() {
    }

    public ReflectionException(@Nonnull String message) {
        super(message);
    }

    public ReflectionException(@Nonnull String message, @Nonnull Throwable cause) {
        super(message, cause);
    }

    public ReflectionException(@Nonnull Throwable cause) {
        super(cause);
    }

    public ReflectionException(@Nullable String message, @Nullable Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
