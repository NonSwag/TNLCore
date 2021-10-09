package net.nonswag.tnl.core.api.language;

import net.nonswag.tnl.core.api.file.helper.JsonHelper;
import net.nonswag.tnl.core.api.logger.Logger;

import javax.annotation.Nonnull;
import java.io.InputStreamReader;
import java.net.URL;

public class Translator {

    @Nonnull
    public static String translate(@Nonnull String string, @Nonnull Language from, @Nonnull Language to) {
        if (from.equals(to)) return string;
        try {
            URL url = new URL("http://api.mymemory.translated.net/get?q=" + string.replace(" ", "%20").toLowerCase() + "&langpair=" + from.getShorthand() + "|" + to.getShorthand());
            InputStreamReader reader = new InputStreamReader(url.openStream());
            String translatedText = JsonHelper.parse(reader).getAsJsonObject().get("responseData").getAsJsonObject().get("translatedText").getAsString();
            return translatedText.replace("\"\"", "").replace(",", "").replace("match", "").replace("-", "").replace("\\\\u", "").replace("\\u", "").replace("~", "");
        } catch (Exception e) {
            Logger.error.println(e.getMessage());
        }
        return string;
    }
}
