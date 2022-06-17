package net.nonswag.tnl.core.api.errors.file;

import javax.annotation.Nonnull;

public class FileSaveException extends FileException {

    public FileSaveException() {
    }

    public FileSaveException(@Nonnull String message) {
        super(message);
    }

    public FileSaveException(@Nonnull String message, @Nonnull Throwable cause) {
        super(message, cause);
    }

    public FileSaveException(@Nonnull Throwable cause) {
        super(cause);
    }
}
