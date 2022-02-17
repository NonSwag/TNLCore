package net.nonswag.tnl.core.api.file;

import javax.annotation.Nonnull;
import java.io.File;
import java.nio.charset.Charset;

public interface Saveable {

    @Nonnull
    Charset getCharset();

    @Nonnull
    File getFile();

    void save();

    default boolean isValid() {
        return getFile().exists();
    }
}
