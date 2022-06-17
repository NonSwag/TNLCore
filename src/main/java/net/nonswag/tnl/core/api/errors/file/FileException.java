package net.nonswag.tnl.core.api.errors.file;

import net.nonswag.tnl.core.api.errors.TNLRuntimeException;

import javax.annotation.Nonnull;

public class FileException extends TNLRuntimeException {

    public FileException() {
    }

    public FileException(@Nonnull String message) {
        super(message);
    }

    public FileException(@Nonnull String message, @Nonnull Throwable cause) {
        super(message, cause);
    }

    public FileException(@Nonnull Throwable cause) {
        super(cause);
    }
}
