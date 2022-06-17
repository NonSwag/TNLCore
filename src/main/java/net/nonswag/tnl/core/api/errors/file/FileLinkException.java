package net.nonswag.tnl.core.api.errors.file;

import javax.annotation.Nonnull;

public class FileLinkException extends FileException {

    public FileLinkException() {
    }

    public FileLinkException(@Nonnull String message) {
        super(message);
    }

    public FileLinkException(@Nonnull String message, @Nonnull Throwable cause) {
        super(message, cause);
    }

    public FileLinkException(@Nonnull Throwable cause) {
        super(cause);
    }
}
