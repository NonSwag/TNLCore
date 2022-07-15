package net.nonswag.tnl.core.api.file.formats;

import lombok.Getter;
import net.nonswag.tnl.core.api.errors.file.FileLoadException;
import net.nonswag.tnl.core.api.errors.file.FileSaveException;
import net.nonswag.tnl.core.api.file.Deletable;
import net.nonswag.tnl.core.api.file.Loadable;
import net.nonswag.tnl.core.api.file.Saveable;
import net.nonswag.tnl.core.api.file.helper.FileHelper;
import net.nonswag.tnl.core.api.logger.Logger;
import net.nonswag.tnl.core.utils.LinuxUtil;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.Arrays;
import java.util.stream.Stream;

@Getter
public class TextFile extends Loadable implements Saveable, Deletable {

    @Nonnull
    private String[] content = new String[]{};
    private boolean sort = false;

    public TextFile(@Nonnull String file) {
        super(file);
        load();
    }

    public TextFile(@Nonnull String path, @Nonnull String file) {
        super(path, file);
        load();
    }

    public TextFile(@Nonnull File file) {
        super(file);
        load();
    }

    @Nonnull
    public final <F extends TextFile> F setContent(@Nonnull String[] content) {
        this.content = content;
        return (F) this;
    }

    @Nonnull
    public <F extends TextFile> F setSort(boolean sort) {
        this.sort = sort;
        return (F) this;
    }

    @Nonnull
    @Override
    protected TextFile load() throws FileLoadException {
        FileHelper.createFile(this.getFile());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.getFile()), getCharset()))) {
            Object[] array = reader.lines().toArray();
            setContent(new String[array.length]);
            for (int i = 0; i < array.length; i++) getContent()[i] = (String) array[i];
        } catch (Exception e) {
            LinuxUtil.Suppressed.runShellCommand("cp " + getFile().getName() + " broken-" + getFile().getName(), getFile().getAbsoluteFile().getParentFile());
            throw new FileLoadException(e);
        }
        if (!isValid()) throw new FileLoadException("file is invalid");
        return this;
    }

    @Override
    public void save() throws FileSaveException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new PrintStream(getFile()), getCharset()))) {
            Stream<String> stream = Arrays.stream(getContent());
            if (this.isSort()) stream = stream.sorted();
            stream.forEach((s) -> {
                try {
                    writer.write(s + "\n");
                } catch (IOException e) {
                    Logger.error.println("Failed to save a line", "content: <'" + s + "'>", e);
                }
            });
        } catch (Exception e) {
            throw new FileSaveException(e);
        }
    }

    @Override
    public final void delete() {
        if (isValid()) getFile().delete();
    }

    @Override
    public String toString() {
        return "TextFile{" +
                "content=" + Arrays.toString(content) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextFile textFile = (TextFile) o;
        return Arrays.equals(content, textFile.content);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(content);
    }
}
