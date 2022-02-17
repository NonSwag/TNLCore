package net.nonswag.tnl.core.api.file.formats;

import net.nonswag.tnl.core.api.file.Deletable;
import net.nonswag.tnl.core.api.file.Loadable;
import net.nonswag.tnl.core.api.file.Saveable;
import net.nonswag.tnl.core.api.file.helper.FileHelper;
import net.nonswag.tnl.core.api.logger.Logger;
import net.nonswag.tnl.core.api.object.Objects;
import net.nonswag.tnl.core.utils.LinuxUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.util.*;

public class PropertyFile extends Loadable implements Saveable, Deletable {

    @Nonnull
    private final HashMap<String, String> values = new HashMap<>();
    @Nonnull
    private List<String> comments = new ArrayList<>();
    private boolean sort = false;

    public PropertyFile(@Nonnull String file) {
        super(file);
        load();
    }

    public PropertyFile(@Nonnull String path, @Nonnull String file) {
        super(path, file);
        load();
    }

    public PropertyFile(@Nonnull File file) {
        super(file);
        load();
    }

    @Nonnull
    public final List<String> getComments() {
        return comments;
    }

    public final void setComments(@Nonnull List<String> comments) {
        this.comments = comments;
    }

    public final void addComment(@Nonnull String comment) {
        getComments().add(comment);
    }

    public final void addCommentIfAbsent(@Nonnull String comment) {
        if (!hasComment(comment)) addComment(comment);
    }

    public final void removeComment(@Nonnull String comment) {
        getComments().removeIf((s) -> s.equals(comment));
    }

    public final boolean hasComment(@Nonnull String comment) {
        return getComments().contains(comment);
    }

    @Nonnull
    public final PropertyFile setSort(boolean sort) {
        this.sort = sort;
        return this;
    }

    public final boolean isSort() {
        return sort;
    }

    @Nonnull
    protected String getDelimeter() {
        return "=";
    }

    @Nonnull
    @Override
    protected final PropertyFile load() {
        FileHelper.createSilent(getFile());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getFile()), getCharset()))) {
            getValues().clear();
            reader.lines().forEach(s -> {
                if (s.startsWith("#")) this.getComments().add(s.substring(1));
                else {
                    List<String> split = Arrays.asList(s.split(this.getDelimeter()));
                    if (split.size() >= 1 && !split.get(0).isEmpty()) {
                        getValues().put(split.get(0), String.join(getDelimeter(), split.subList(1, split.size())));
                    }
                }
            });
            save();
        } catch (Exception e) {
            LinuxUtil.Suppressed.runShellCommand("cp " + getFile().getName() + " broken-" + getFile().getName(), getFile().getAbsoluteFile().getParentFile());
            e.printStackTrace();
        } finally {
            if (!isValid()) Logger.error.println("The file <'" + getFile().getAbsolutePath() + "'> is invalid");
        }
        return this;
    }

    @Override
    public final void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFile(), getCharset()))) {
            getComments().forEach((comment) -> {
                try {
                    writer.write("#" + comment.replace("\n", "\\n") + "\n");
                } catch (IOException e) {
                    Logger.error.println("Failed to save a comment", "content: <'" + comment + "'>", e);
                }
            });
            getValues().forEach((key, value) -> {
                try {
                    writer.write((key + this.getDelimeter() + value).replace("\n", "\\n") + "\n");
                } catch (IOException e) {
                    Logger.error.println("Failed to save a property", "content: <'" + key + getDelimeter() + value + "'>", e);
                }
            });
        } catch (Exception var6) {
            Logger.error.println("Failed to save file <'" + getFile().getAbsolutePath() + "'>", var6);
        }
    }

    @Override
    public final void delete() {
        if (isValid()) getFile().delete();
    }

    public void setValueIfAbsent(@Nonnull String key, @Nonnull String value) {
        if (!has(key)) setValue(key, value);
    }

    public void setValueIfAbsent(@Nonnull String key, @Nonnull Collection<String> value) {
        setValueIfAbsent(key, String.join(", ", value));
    }

    public void setValueIfAbsent(@Nonnull String key, @Nonnull Boolean value) {
        setValueIfAbsent(key, value.toString());
    }

    public void setValueIfAbsent(@Nonnull String key, @Nonnull Number value) {
        setValueIfAbsent(key, value.toString());
    }

    public void setValueIfAbsent(@Nonnull String key, @Nonnull Character value) {
        setValueIfAbsent(key, value.toString());
    }

    public void setValue(@Nonnull String key, @Nonnull String value) {
        getValues().put(key, value);
    }

    public void setValue(@Nonnull String key, @Nonnull Collection<String> value) {
        getValues().put(key, String.join(", ", value));
    }

    public void setValue(@Nonnull String key, @Nonnull Boolean value) {
        getValues().put(key, String.valueOf(value));
    }

    public void setValue(@Nonnull String key, @Nonnull Number value) {
        getValues().put(key, String.valueOf(value));
    }

    public void setValue(@Nonnull String key, @Nonnull Character value) {
        getValues().put(key, String.valueOf(value));
    }

    public boolean has(@Nonnull String key) {
        return getValues().containsKey(key);
    }

    @Nonnull
    public String getOrDefault(@Nonnull String key, @Nonnull String defaultValue) {
        return getValues().getOrDefault(key, defaultValue);
    }

    @Nullable
    public String getString(@Nonnull String key) {
        return getValues().get(key);
    }

    @Nonnull
    public Objects<String> get(@Nonnull String key) {
        return new Objects<>(getString(key));
    }

    @Nonnull
    public List<String> getStringList(@Nonnull String key) {
        try {
            String string = getString(key);
            if (string != null && !string.isEmpty()) {
                return Arrays.asList(get(key).nonnull().split(", "));
            }
        } catch (Exception ignored) {
        }
        return new ArrayList<>();
    }

    public int getInteger(@Nonnull String key) {
        try {
            return Integer.parseInt(get(key).nonnull());
        } catch (Exception ignored) {
            return 0;
        }
    }

    public double getDouble(@Nonnull String key) {
        try {
            return Double.parseDouble(get(key).nonnull());
        } catch (Exception e) {
            return 0D;
        }
    }

    public float getFloat(@Nonnull String key) {
        try {
            return Float.parseFloat(get(key).nonnull());
        } catch (Exception e) {
            return 0F;
        }
    }

    public short getShort(@Nonnull String key) {
        try {
            return Short.parseShort(get(key).nonnull());
        } catch (Exception e) {
            return 0;
        }
    }

    public byte getByte(@Nonnull String key) {
        try {
            return Byte.parseByte(get(key).nonnull());
        } catch (Exception e) {
            return 0;
        }
    }

    public long getLong(@Nonnull String key) {
        try {
            return Long.parseLong(get(key).nonnull());
        } catch (Exception e) {
            return 0L;
        }
    }

    @Nullable
    public Character getCharacter(@Nonnull String key) {
        try {
            return get(key).nonnull().charAt(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public char[] getCharacters(@Nonnull String key) {
        try {
            return get(key).nonnull().toCharArray();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean getBoolean(@Nonnull String key) {
        try {
            return Boolean.parseBoolean(get(key).nonnull());
        } catch (Exception e) {
            return false;
        }
    }

    public void removeValue(@Nonnull String key) {
        getValues().remove(key);
    }

    @Nonnull
    public final HashMap<String, String> getValues() {
        return values;
    }
}
