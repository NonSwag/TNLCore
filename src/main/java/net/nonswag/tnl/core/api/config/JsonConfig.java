package net.nonswag.tnl.core.api.config;

import com.google.gson.*;
import net.nonswag.tnl.core.api.file.FileHelper;
import net.nonswag.tnl.core.api.logger.Logger;
import net.nonswag.tnl.core.utils.LinuxUtil;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonConfig implements Config {

    @Nonnull
    private final File file;
    @Nonnull
    private JsonElement jsonElement;

    public JsonConfig(@Nonnull String file) {
        this(new File(file));
    }

    public JsonConfig(@Nonnull String path, @Nonnull String file) {
        this(new File(new File(path), file));
    }

    public JsonConfig(@Nonnull File file) {
        this.file = file;
        JsonElement jsonElement = new JsonObject();
        try {
            FileHelper.create(getFile());
            jsonElement = JsonParser.parseReader(new BufferedReader(new InputStreamReader(new FileInputStream(getFile()), StandardCharsets.UTF_8)));
            if (jsonElement instanceof JsonNull) jsonElement = new JsonObject();
        } catch (Exception e) {
            LinuxUtil.runSafeShellCommand("cp " + getFile().getName() + " broken-" + getFile().getName(), getFile().getAbsoluteFile().getParentFile());
            Logger.error.println("Failed to load file <'" + getFile().getAbsolutePath() + "'>", "Creating Backup of the old file");
        }
        this.jsonElement = jsonElement;
        save();
        if (!isValid()) Logger.error.println("The file <'" + file.getAbsolutePath() + "'> is invalid");
    }

    @Nonnull
    public File getFile() {
        return file;
    }

    public void setJsonElement(@Nonnull JsonElement jsonElement) {
        this.jsonElement = jsonElement;
    }

    @Nonnull
    public JsonElement getJsonElement() {
        return jsonElement;
    }

    @Override
    public void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new PrintStream(getFile()), StandardCharsets.UTF_8));
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(getJsonElement()));
            writer.close();
        } catch (Exception e) {
            Logger.error.println("Failed to save file <'" + getFile().getAbsolutePath() + "'>", e.getMessage());
        }
    }
}

