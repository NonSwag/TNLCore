package net.nonswag.tnl.core.api.file.formats.spearat;

import net.nonswag.tnl.core.api.errors.file.FileLoadException;
import net.nonswag.tnl.core.api.errors.file.FileSaveException;
import net.nonswag.tnl.core.api.file.Deletable;
import net.nonswag.tnl.core.api.file.Loadable;
import net.nonswag.tnl.core.api.file.Saveable;
import net.nonswag.tnl.core.api.file.helper.FileHelper;
import net.nonswag.tnl.core.utils.LinuxUtil;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class SeparatorFile extends Loadable implements Saveable, Deletable {

    @Nonnull
    private List<List<String>> entries = new ArrayList<>(new ArrayList<>());

    protected SeparatorFile(@Nonnull String file) {
        super(file);
        load();
    }

    protected SeparatorFile(@Nonnull String path, @Nonnull String file) {
        super(path, file);
        load();
    }

    protected SeparatorFile(@Nonnull File file) {
        super(file);
        load();
    }

    @Nonnull
    public final List<List<String>> getEntries() {
        return entries;
    }

    public final void setEntries(@Nonnull List<List<String>> entries) {
        this.entries = entries;
    }

    public void insert(@Nonnull Object... insertion) {
        List<String> insert = new ArrayList<>();
        for (Object o : insertion) insert.add(o.toString());
        getEntries().add(insert);
    }

    public boolean remove(@Nonnull Object... objects) {
        return getEntries().removeIf(entry -> {
            for (Object object : objects) if (!entry.contains(object.toString())) return false;
            return true;
        });
    }

    @Nonnull
    public List<List<String>> select(@Nonnull Object... objects) {
        List<List<String>> selection = new ArrayList<>(new ArrayList<>());
        l:
        for (List<String> entry : getEntries()) {
            for (Object object : objects) if (!entry.contains(object.toString())) continue l;
            selection.add(entry);
        }
        return selection;
    }

    @Nonnull
    @Override
    protected final SeparatorFile load() throws FileLoadException {
        FileHelper.create(getFile());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getFile()), getCharset()))) {
            getEntries().clear();
            reader.lines().forEach(s -> {
                if (!s.isEmpty()) getEntries().add(Arrays.asList(s.split(getDelimiter())));
            });
            save();
        } catch (Exception e) {
            LinuxUtil.Suppressed.runShellCommand("cp " + getFile().getName() + " broken-" + getFile().getName(), getFile().getAbsoluteFile().getParentFile());
            throw new FileLoadException(e);
        }
        if (!isValid()) throw new FileLoadException("file is invalid");
        return this;
    }

    @Override
    public final void save() throws FileSaveException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new PrintStream(this.getFile()), getCharset()))) {
            for (List<String> entry : getEntries()) writer.write(String.join(getDelimiter(), entry) + "\n");
        } catch (Exception e) {
            throw new FileSaveException(e);
        }
    }

    @Override
    public final void delete() {
        if (isValid()) getFile().delete();
    }

    @Nonnull
    protected abstract String getDelimiter();
}
