package net.nonswag.tnl.core.api.file.formats.spearat;

import net.nonswag.tnl.core.api.file.Loadable;
import net.nonswag.tnl.core.api.file.Saveable;
import net.nonswag.tnl.core.api.file.helper.FileHelper;
import net.nonswag.tnl.core.api.logger.Logger;
import net.nonswag.tnl.core.utils.LinuxUtil;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class SeparatorFile extends Loadable implements Saveable {

    @Nonnull
    private List<List<String>> entries = new ArrayList<>(new ArrayList<>());

    protected SeparatorFile(@Nonnull String file) {
        super(file);
    }

    protected SeparatorFile(@Nonnull String path, @Nonnull String file) {
        super(path, file);
    }

    protected SeparatorFile(@Nonnull File file) {
        super(file);
    }

    @Nonnull
    public final List<List<String>> getEntries() {
        return entries;
    }

    private void setEntries(@Nonnull List<List<String>> entries) {
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
    protected final SeparatorFile load() {
        FileHelper.createSilent(this.getFile());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.getFile()), StandardCharsets.UTF_8))) {
            getEntries().clear();
            reader.lines().forEach(s -> {
                if (!s.isEmpty()) getEntries().add(Arrays.asList(s.split(getDelimiter())));
            });
            save();
        } catch (Exception e) {
            LinuxUtil.runSafeShellCommand("cp " + this.getFile().getName() + " broken-" + this.getFile().getName(), this.getFile().getAbsoluteFile().getParentFile());
            Logger.error.println("Failed to load file <'" + this.getFile().getAbsolutePath() + "'>", "Creating Backup of the old file", e);
        } finally {
            if (!this.isValid()) {
                Logger.error.println("The file <'" + this.getFile().getAbsolutePath() + "'> is invalid");
            }
        }
        return this;
    }

    @Override
    public final void save() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new PrintStream(this.getFile()), StandardCharsets.UTF_8))) {
            for (List<String> entry : getEntries()) writer.write(String.join(getDelimiter(), entry) + "\n");
        } catch (Exception var6) {
            Logger.error.println("Failed to save file <'" + this.getFile().getAbsolutePath() + "'>", var6);
        }
    }

    @Nonnull
    protected abstract String getDelimiter();
}
