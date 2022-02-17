package net.nonswag.tnl.core.api.file.formats.spearat;

import javax.annotation.Nonnull;
import java.io.File;

public class TSVFile extends SeparatorFile {

    public TSVFile(@Nonnull String file) {
        super(file);
    }

    public TSVFile(@Nonnull String path, @Nonnull String file) {
        super(path, file);
    }

    public TSVFile(@Nonnull File file) {
        super(file);
    }

    @Nonnull
    @Override
    protected String getDelimiter() {
        return "\t";
    }
}
