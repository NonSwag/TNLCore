package net.nonswag.tnl.core.utils;

import javax.annotation.Nonnull;

public class SystemUtil {

    @Nonnull
    public static final Type TYPE;

    static {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("nix") || os.contains("nux") || os.contains("aix")) TYPE = Type.LINUX;
        else if (os.contains("sunos")) TYPE = Type.SUNOS;
        else if (os.contains("mac")) TYPE = Type.MAC;
        else if (os.contains("win")) TYPE = Type.WINDOWS;
        else TYPE = Type.UNKNOWN;
    }

    public enum Type {
        UNKNOWN,
        LINUX,
        SUNOS,
        MAC,
        WINDOWS
    }
}
