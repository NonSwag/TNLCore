package net.nonswag.tnl.core.api.file.helper;

import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public final class JsonHelper {

    @Getter
    @Setter
    private static boolean lenient = true;

    private JsonHelper() {
    }

    @Nonnull
    public static JsonElement parse(@Nonnull String json) throws JsonSyntaxException {
        return parse(new StringReader(json));
    }

    @Nonnull
    public static JsonElement parse(@Nonnull Reader reader) throws JsonParseException {
        try {
            JsonReader jsonReader = new JsonReader(reader);
            JsonElement element = parse(jsonReader);
            if (!element.isJsonNull() && jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonSyntaxException("Did not consume the entire document.");
            } else return element;
        } catch (MalformedJsonException | NumberFormatException e) {
            throw new JsonSyntaxException(e);
        } catch (IOException e) {
            throw new JsonIOException(e);
        }
    }

    @Nonnull
    public static JsonElement parse(@Nonnull JsonReader reader) throws JsonParseException {
        reader.setLenient(isLenient());
        return Streams.parse(reader);
    }
}
