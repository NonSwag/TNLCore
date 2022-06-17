package net.nonswag.tnl.core.api.errors.file;

import javax.annotation.Nonnull;

public class FileDeleteException extends FileException {

    public FileDeleteException() {
    }

    public FileDeleteException(@Nonnull String message) {
        super(message);
    }

    public FileDeleteException(@Nonnull String message, @Nonnull Throwable cause) {
        super(message, cause);
    }

    public FileDeleteException(@Nonnull Throwable cause) {
        super(cause);
    }
}
