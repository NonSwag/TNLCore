package net.nonswag.tnl.core.api.file.formats;

import net.nonswag.tnl.core.api.file.Deletable;
import net.nonswag.tnl.core.api.file.Loadable;
import net.nonswag.tnl.core.api.file.Saveable;
import net.nonswag.tnl.core.api.file.helper.FileHelper;
import net.nonswag.tnl.core.api.logger.Logger;
import net.nonswag.tnl.core.utils.LinuxUtil;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;

public class TextFile extends Loadable implements Saveable, Deletable {

    @Nonnull
    private String[] content = new String[]{};
    private boolean sort = false;

    public TextFile(@Nonnull String file) {
        super(file);
    }

    public TextFile(@Nonnull String path, @Nonnull String file) {
        super(path, file);
    }

    public TextFile(@Nonnull File file) {
        super(file);
        load();
    }

    @Nonnull
    public final TextFile setContent(@Nonnull String[] content) {
        this.content = content;
        return this;
    }

    @Nonnull
    public final String[] getContent() {
        return content;
    }

    @Nonnull
    public TextFile setSort(boolean sort) {
        this.sort = sort;
        return this;
    }

    public boolean isSort() {
        return sort;
    }

    @Nonnull
    @Override
    protected TextFile load() {
        FileHelper.createSilent(this.getFile());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.getFile()), StandardCharsets.UTF_8))) {
            Object[] array = reader.lines().toArray();
            setContent(new String[array.length]);
            for (int i = 0; i < array.length; i++) getContent()[i] = (String) array[i];
            save();
        } catch (Exception e) {
            LinuxUtil.Suppressed.runShellCommand("cp " + getFile().getName() + " broken-" + getFile().getName(), getFile().getAbsoluteFile().getParentFile());
            Logger.error.println("Failed to load file <'" + getFile().getAbsolutePath() + "'>", "Creating Backup of the old file", e);
        } finally {
            if (!this.isValid()) {
                Logger.error.println("The file <'" + getFile().getAbsolutePath() + "'> is invalid");
            }
        }
        return this;
    }

    @Override
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new PrintStream(getFile()), StandardCharsets.UTF_8))) {
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
            Logger.error.println("Failed to save file <'" + getFile().getAbsolutePath() + "'>", e);
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
