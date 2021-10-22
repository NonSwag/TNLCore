package net.nonswag.tnl.core.utils;

import net.nonswag.tnl.core.api.math.MathUtil;

import javax.annotation.Nonnull;
import java.text.DecimalFormat;

public final class StringUtil {

    @Nonnull
    public static String VALID_CHARS = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";

    private StringUtil() {
    }

    @Nonnull
    public static String random(int length) {
        StringBuilder randomString = new StringBuilder();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = VALID_CHARS.charAt(MathUtil.randomInteger(0, VALID_CHARS.length() - 1));
        }
        for (char c : text) randomString.append(c);
        return randomString.toString();
    }

    @Nonnull
    public static String format(@Nonnull String pattern, double number) {
        return new DecimalFormat(pattern).format(number);
    }
}
