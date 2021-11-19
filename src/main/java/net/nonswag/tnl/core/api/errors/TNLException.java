package net.nonswag.tnl.core.api.errors;

import net.nonswag.tnl.core.api.logger.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.PrintStream;
import java.io.PrintWriter;

public class TNLException extends Exception {

    public TNLException() {
    }

    public TNLException(@Nonnull String message) {
        super(message);
    }

    public TNLException(@Nonnull String message, @Nonnull Throwable cause) {
        super(message, cause);
    }

    public TNLException(@Nonnull Throwable cause) {
        super(cause);
    }

    public TNLException(@Nullable String message, @Nullable Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public void printStackTrace(@Nonnull Logger logger) {
        logger.println(this);
    }

    @Override
    public void printStackTrace() {
        printStackTrace(Logger.error);
    }

    @Override
    public void printStackTrace(@Nullable PrintStream s) {
        printStackTrace();
    }

    @Override
    public void printStackTrace(@Nullable PrintWriter s) {
        printStackTrace();
    }
}
