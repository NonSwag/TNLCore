package net.nonswag.tnl.core.api.file.formats;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.core.api.errors.file.FileLoadException;
import net.nonswag.tnl.core.api.errors.file.FileSaveException;
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

@Getter
@Setter
public class PropertyFile extends Loadable implements Saveable, Deletable {

    @Nonnull
    private final TreeMap<String, String> values = new TreeMap<>();
    @Nonnull
    private TreeSet<String> comments = new TreeSet<>();

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

    public void addComment(@Nonnull String comment) {
        getComments().add(comment);
    }

    public void addCommentIfAbsent(@Nonnull String comment) {
        if (!hasComment(comment)) addComment(comment);
    }

    public void removeComment(@Nonnull String comment) {
        getComments().removeIf((s) -> s.equals(comment));
    }

    public boolean hasComment(@Nonnull String comment) {
        return getComments().contains(comment);
    }

    @Nonnull
    public String getDelimiter() {
        return "=";
    }

    @Nonnull
    @Override
    protected PropertyFile load() throws FileLoadException {
        FileHelper.create(getFile());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getFile()), getCharset()))) {
            getValues().clear();
            reader.lines().forEach(s -> {
                if (!s.startsWith("#")) {
                    List<String> split = Arrays.asList(s.split(getDelimiter()));
                    if (split.size() >= 1 && !split.get(0).isEmpty()) {
                        getValues().put(split.get(0), String.join(getDelimiter(), split.subList(1, split.size())));
                    }
                } else getComments().add(s.substring(1));
            });
        } catch (Exception e) {
            LinuxUtil.Suppressed.runShellCommand("cp " + getFile().getName() + " broken-" + getFile().getName(), getFile().getAbsoluteFile().getParentFile());
            throw new FileLoadException(e);
        }
        if (!isValid()) throw new FileLoadException("file is invalid");
        return this;
    }

    @Override
    public void save() throws FileSaveException {
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
                    writer.write((key + this.getDelimiter() + value).replace("\n", "\\n") + "\n");
                } catch (IOException e) {
                    Logger.error.println("Failed to save a property", "content: <'" + key + getDelimiter() + value + "'>", e);
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
    public final TreeMap<String, String> getValues() {
        return values;
    }
}
