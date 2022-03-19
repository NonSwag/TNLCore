package net.nonswag.tnl.core.api.sql;

import net.nonswag.tnl.core.api.file.formats.PropertyFile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Database {

    @Nullable
    private static SQLConnection connection = null;

    @Nonnull
    public static synchronized SQLConnection getConnection() {
        return connection == null ? (connection = SQL.connect(new PropertyFile("Core/.sql", "config.properties"))) : connection;
    }

    public static synchronized void disconnect() {
        if (connection != null) connection.close();
    }
}
