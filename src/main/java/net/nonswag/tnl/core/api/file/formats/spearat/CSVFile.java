package net.nonswag.tnl.core.api.file.formats.spearat;

import javax.annotation.Nonnull;
import java.io.File;

public class CSVFile extends SeparatorFile {

    public CSVFile(@Nonnull String file) {
        super(file);
    }

    public CSVFile(@Nonnull String path, @Nonnull String file) {
        super(path, file);
    }

    public CSVFile(@Nonnull File file) {
        super(file);
    }

    @Nonnull
    @Override
    protected String getDelimiter() {
        return ",";
    }
}
