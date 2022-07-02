package net.nonswag.tnl.core.utils;

import net.nonswag.tnl.core.api.math.MathUtil;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

public final class StringUtil {

    @Nonnull
    public static String VALID_CHARS = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890";

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

    @Nonnull
    public static byte[][] toByteArray(@Nonnull String... strings) {
        byte[][] bytes = new byte[strings.length][];
        for (int i = 0; i < strings.length; i++) bytes[i] = strings[i].getBytes(StandardCharsets.UTF_8);
        return bytes;
    }

    @Nonnull
    public static String roman(int i) {
        return switch (i) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            case 5 -> "V";
            case 6 -> "VI";
            case 7 -> "VII";
            case 8 -> "VIII";
            case 9 -> "IX";
            case 10 -> "X";
            case 11 -> "XI";
            case 12 -> "XII";
            case 13 -> "XIII";
            case 14 -> "XIV";
            case 15 -> "XV";
            case 16 -> "XVI";
            case 17 -> "XVII";
            case 18 -> "XVIII";
            case 19 -> "XIX";
            case 20 -> "XX";
            case 21 -> "XXI";
            case 22 -> "XXII";
            case 23 -> "XXIII";
            case 30 -> "XXX";
            case 40 -> "XL";
            case 50 -> "L";
            case 60 -> "LX";
            case 70 -> "LXX";
            case 80 -> "LXXX";
            case 90 -> "XC";
            case 100 -> "C";
            case 200 -> "CC";
            case 300 -> "CCC";
            case 400 -> "CD";
            case 500 -> "D";
            case 1000 -> "M";
            default -> String.valueOf(i);
        };
    }

    public static boolean isPalindrome(@Nonnull String string) {
        StringBuilder reversed = new StringBuilder();
        for (int index = string.length() - 1; index >= 0; index--) reversed.append(string.charAt(index));
        return string.equals(reversed.toString());
    }
}
