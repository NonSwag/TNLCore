package net.nonswag.tnl.core.api.config;

import javax.annotation.Nonnull;
import java.io.File;

public interface Config {

    @Nonnull
    File getFile();

    void save();

    default boolean isValid() {
        return getFile().exists() && getFile().isFile();
    }
}
