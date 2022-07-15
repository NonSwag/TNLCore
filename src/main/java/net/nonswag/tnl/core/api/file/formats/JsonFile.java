package net.nonswag.tnl.core.api.file.formats;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.core.api.errors.file.FileLoadException;
import net.nonswag.tnl.core.api.errors.file.FileSaveException;
import net.nonswag.tnl.core.api.file.Deletable;
import net.nonswag.tnl.core.api.file.Loadable;
import net.nonswag.tnl.core.api.file.Saveable;
import net.nonswag.tnl.core.api.file.helper.FileHelper;
import net.nonswag.tnl.core.api.file.helper.JsonHelper;
import net.nonswag.tnl.core.utils.LinuxUtil;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.Objects;

@Getter
@Setter
public class JsonFile extends Loadable implements Saveable, Deletable {

    @Nonnull
    private JsonElement jsonElement = new JsonObject();

    public JsonFile(@Nonnull String file) {
        super(file);
        load();
    }

    public JsonFile(@Nonnull String path, @Nonnull String file) {
        super(path, file);
        load();
    }

    public JsonFile(@Nonnull File file) {
        super(file);
        load();
    }

    @Nonnull
    @Override
    protected JsonFile load() throws FileLoadException {
        FileHelper.createFile(getFile());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getFile()), getCharset()))) {
            jsonElement = JsonHelper.parse(reader);
            if (jsonElement instanceof JsonNull) this.jsonElement = new JsonObject();
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
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(getJsonElement()));
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
        return "JsonFile{" +
                "jsonElement=" + jsonElement +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonFile jsonFile = (JsonFile) o;
        return jsonElement.equals(jsonFile.jsonElement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jsonElement);
    }
}

