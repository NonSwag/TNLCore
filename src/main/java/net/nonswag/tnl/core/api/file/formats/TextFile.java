package net.nonswag.tnl.core.api.file.formats;

import net.nonswag.tnl.core.api.file.Loadable;
import net.nonswag.tnl.core.api.file.Saveable;
import net.nonswag.tnl.core.api.file.helper.FileHelper;
import net.nonswag.tnl.core.api.logger.Logger;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class TextFile extends Loadable implements Saveable {

    @Nonnull
    private String[] content = new String[]{};

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

    public void setContent(@Nonnull String[] content) {
        this.content = content;
    }

    @Nonnull
    public String[] getContent() {
        return content;
    }

    @Nonnull
    @Override
    protected TextFile load() {
        try {
            FileHelper.create(getFile());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(getFile()), StandardCharsets.UTF_8));
            Object[] array = bufferedReader.lines().toArray();
            this.content = new String[array.length];
            for (int i = 0; i < array.length; i++) content[i] = (String) array[i];
            bufferedReader.close();
            save();
        } catch (Exception e) {
            Logger.error.println(e);
        } finally {
            if (!isValid()) Logger.error.println("The file <'" + getFile().getAbsolutePath() + "'> is invalid");
        }
        return this;
    }

    @Override
    public void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new PrintStream(getFile()), StandardCharsets.UTF_8));
            for (String s : content) writer.write(s + "\n");
            writer.close();
        } catch (Exception e) {
            Logger.error.println("Failed to save file <'" + getFile().getAbsolutePath() + "'>", e);
        }
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
