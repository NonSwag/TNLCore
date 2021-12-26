package net.nonswag.tnl.core.api.file.formats;

import net.nonswag.tnl.core.api.message.Placeholder;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShellFile extends TextFile {

    @Nonnull
    private final List<Placeholder> placeholders = new ArrayList<>();

    public ShellFile(@Nonnull String file) {
        super(file);
        load();
    }

    public ShellFile(@Nonnull String path, @Nonnull String file) {
        super(path, file);
        load();
    }

    public ShellFile(@Nonnull File file) {
        super(file);
        load();
    }

    @Override
    public final boolean isSort() {
        return false;
    }

    @Nonnull
    @Override
    public final ShellFile setSort(boolean sort) {
        return this;
    }

    @Nonnull
    public List<Placeholder> getPlaceholders() {
        return placeholders;
    }

    @Nonnull
    public ShellFile registerPlaceholder(@Nonnull Placeholder placeholder) {
        if (!getPlaceholders().contains(placeholder)) getPlaceholders().add(placeholder);
        return this;
    }

    @Nonnull
    public ShellFile unregisterPlaceholder(@Nonnull Placeholder placeholder) {
        getPlaceholders().remove(placeholder);
        return this;
    }
}
