package net.nonswag.tnl.core.api.errors.file;

import javax.annotation.Nonnull;

public class FileCreateException extends FileException {

    public FileCreateException() {
    }

    public FileCreateException(@Nonnull String message) {
        super(message);
    }

    public FileCreateException(@Nonnull String message, @Nonnull Throwable cause) {
        super(message, cause);
    }

    public FileCreateException(@Nonnull Throwable cause) {
        super(cause);
    }
}
