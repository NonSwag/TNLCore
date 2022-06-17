package net.nonswag.tnl.core.api.file;

import net.nonswag.tnl.core.api.errors.file.FileSaveException;

import javax.annotation.Nonnull;
import java.io.File;
import java.nio.charset.Charset;

public interface Saveable {

    @Nonnull
    Charset getCharset();

    @Nonnull
    File getFile();

    void save() throws FileSaveException;

    default boolean isValid() {
        return getFile().exists();
    }
}
