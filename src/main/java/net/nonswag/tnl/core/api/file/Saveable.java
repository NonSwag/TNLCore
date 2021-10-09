package net.nonswag.tnl.core.api.file;

import javax.annotation.Nonnull;
import java.io.File;

public interface Saveable {

    @Nonnull
    File getFile();

    void save();

    default boolean isValid() {
        return getFile().exists() && getFile().isFile();
    }
}
