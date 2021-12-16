package net.nonswag.tnl.core.api.errors;

import javax.annotation.Nonnull;

public class DownloadException extends TNLRuntimeException {

    public DownloadException() {
    }

    public DownloadException(@Nonnull String message) {
        super(message);
    }

    public DownloadException(@Nonnull String message, @Nonnull Throwable cause) {
        super(message, cause);
    }

    public DownloadException(@Nonnull Throwable cause) {
        super(cause);
    }
}
