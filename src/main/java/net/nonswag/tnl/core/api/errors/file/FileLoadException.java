package net.nonswag.tnl.core.api.errors.file;

import javax.annotation.Nonnull;

public class FileLoadException extends FileException {

    public FileLoadException() {
    }

    public FileLoadException(@Nonnull String message) {
        super(message);
    }

    public FileLoadException(@Nonnull String message, @Nonnull Throwable cause) {
        super(message, cause);
    }

    public FileLoadException(@Nonnull Throwable cause) {
        super(cause);
    }
}
