package net.nonswag.tnl.core.api.sql;

import net.nonswag.tnl.core.api.file.formats.PropertyFile;
import net.nonswag.tnl.core.api.logger.Logger;

import javax.annotation.Nonnull;

public final class SQL {

    private SQL() {
    }

    @Nonnull
    public static SQLConnection connect(@Nonnull PropertyFile config) {
        config.setValueIfAbsent("url", "jdbc:mysql://127.0.0.1:3306/mysql?autoReconnect=true");
        config.setValueIfAbsent("username", "root");
        config.setValueIfAbsent("password", "secret");
        config.setValueIfAbsent("driver", "com.mysql.cj.jdbc.Driver");
        config.save();
        String url = config.get("url").nonnull();
        String username = config.get("username").nonnull();
        String password = config.get("password").nonnull();
        String driver = config.get("driver").nonnull();
        SQLConnection connection = new SQLConnection(url, username, password, driver);
        Logger.debug.println("Connected to database: " + url);
        return connection;
    }
}
