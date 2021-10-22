package net.nonswag.tnl.core.api.file.formats;

import com.google.gson.*;
import net.nonswag.tnl.core.api.file.Loadable;
import net.nonswag.tnl.core.api.file.Saveable;
import net.nonswag.tnl.core.api.file.helper.FileHelper;
import net.nonswag.tnl.core.api.file.helper.JsonHelper;
import net.nonswag.tnl.core.api.logger.Logger;
import net.nonswag.tnl.core.utils.LinuxUtil;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class JsonFile extends Loadable implements Saveable {

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

    public final void setJsonElement(@Nonnull JsonElement jsonElement) {
        this.jsonElement = jsonElement;
    }

    @Nonnull
    public final JsonElement getJsonElement() {
        return jsonElement;
    }

    @Nonnull
    @Override
    protected final JsonFile load() {
        FileHelper.createSilent(getFile());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getFile()), StandardCharsets.UTF_8))) {
            jsonElement = JsonHelper.parse(reader);
            if (jsonElement instanceof JsonNull) this.jsonElement = new JsonObject();
            save();
        } catch (Exception e) {
            LinuxUtil.runSafeShellCommand("cp " + getFile().getName() + " broken-" + getFile().getName(), getFile().getAbsoluteFile().getParentFile());
            Logger.error.println("Failed to load file <'" + getFile().getAbsolutePath() + "'>", "Creating Backup of the old file");
        } finally {
            if (!isValid()) Logger.error.println("The file <'" + getFile().getAbsolutePath() + "'> is invalid");
        }
        return this;
    }

    @Override
    public final void save() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new PrintStream(getFile()), StandardCharsets.UTF_8))) {
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(getJsonElement()));
        } catch (Exception e) {
            Logger.error.println("Failed to save file <'" + getFile().getAbsolutePath() + "'>", e.getMessage());
        }
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

