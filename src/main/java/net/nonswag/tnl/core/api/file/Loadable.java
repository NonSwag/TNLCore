package net.nonswag.tnl.core.api.file;

import net.nonswag.tnl.core.api.errors.file.FileLoadException;

import javax.annotation.Nonnull;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public abstract class Loadable {

    @Nonnull
    private final File file;

    protected Loadable(@Nonnull String file) {
        this(new File(file));
    }

    protected Loadable(@Nonnull String path, @Nonnull String file) {
        this(new File(path, file));
    }

    protected Loadable(@Nonnull File file) {
        this.file = file;
    }

    @Nonnull
    protected abstract Loadable load() throws FileLoadException;

    @Nonnull
    public Charset getCharset() {
        return StandardCharsets.UTF_8;
    }

    @Nonnull
    public final File getFile() {
        return file;
    }
}
