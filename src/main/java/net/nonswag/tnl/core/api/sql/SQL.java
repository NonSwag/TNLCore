package net.nonswag.tnl.core.api.sql;

import net.nonswag.tnl.core.api.file.formats.PropertyFile;
import net.nonswag.tnl.core.api.logger.Logger;

import javax.annotation.Nonnull;
import java.sql.SQLException;
import java.util.Objects;

public final class SQL {

    private SQL() {
    }

    @Nonnull
    public static SQLConnection connect(@Nonnull PropertyFile config) {
        SQLConnection connection = null;
        config.setValueIfAbsent("url", "jdbc:mysql://127.0.0.1:3306/");
        config.setValueIfAbsent("database", "mysql");
        config.setValueIfAbsent("username", "root");
        config.setValueIfAbsent("password", "secret");
        config.setValueIfAbsent("driver", "com.mysql.cj.jdbc.Driver");
        config.save();
        String url = config.getString("url");
        String database = config.getString("database");
        String username = config.getString("username");
        String password = config.getString("password");
        String driver = config.getString("driver");
        if (!(Objects.equals(url, "jdbc:mysql://127.0.0.1:3306/") && Objects.equals(database, "mysql") &&
                Objects.equals(username, "root") && Objects.equals(password, "secret") &&
                Objects.equals(driver, "com.mysql.cj.jdbc.Driver"))) {
            if (url != null && database != null && username != null && password != null && driver != null) {
                try {
                    connection = new SQLConnection(url, database, username, password, driver);
                    Logger.debug.println("Connected to database");
                } catch (SQLException e) {
                    Logger.error.println("Can't connect to database", e);
                }
            } else Logger.error.println("Given Database parameters are invalid");
        }
        assert connection != null : "Cannot get <'SQLConnection'> because <'connection'> is null";
        return connection;
    }
}
