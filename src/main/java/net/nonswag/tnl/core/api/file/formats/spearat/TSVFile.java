package net.nonswag.tnl.core.api.file.formats.spearat;

import javax.annotation.Nonnull;
import java.io.File;

public class TSVFile extends SeparatorFile {

    public TSVFile(@Nonnull String file) {
        super(file);
        load();
    }

    public TSVFile(@Nonnull String path, @Nonnull String file) {
        super(path, file);
        load();
    }

    public TSVFile(@Nonnull File file) {
        super(file);
        load();
    }

    @Nonnull
    @Override
    protected String getDelimiter() {
        return "\t";
    }
}
