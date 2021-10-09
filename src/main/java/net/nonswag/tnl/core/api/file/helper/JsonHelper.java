package net.nonswag.tnl.core.api.file.helper;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class JsonHelper {

    @Nonnull
    public static JsonElement parse(@Nonnull String json) throws JsonSyntaxException {
        return parse(new StringReader(json));
    }

    @Nonnull
    public static JsonElement parse(@Nonnull Reader reader) throws JsonIOException, JsonSyntaxException {
        try {
            JsonReader jsonReader = new JsonReader(reader);
            JsonElement element = parse(jsonReader);
            if (!element.isJsonNull() && jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonSyntaxException("Did not consume the entire document.");
            } else return element;
        } catch (MalformedJsonException | NumberFormatException var3) {
            throw new JsonSyntaxException(var3);
        } catch (IOException var4) {
            throw new JsonIOException(var4);
        }
    }

    @Nonnull
    public static JsonElement parse(@Nonnull JsonReader reader) throws JsonIOException, JsonSyntaxException {
        boolean lenient = reader.isLenient();
        reader.setLenient(true);
        try {
            return Streams.parse(reader);
        } catch (StackOverflowError | OutOfMemoryError var7) {
            throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", var7);
        } finally {
            reader.setLenient(lenient);
        }
    }
}
